package com.example.oauth2.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

/**
 * Created by wanganbang on 7/25/16.
 */
@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource(name = "redisConnectionFactory")
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //oauth2认证包括两个部分认证， 一般来说都会让用户界别和客户端级别都进行认证之后才会获得最终token，只有单纯的客户端模式的时候不需要i用户级别认证
        clients.inMemory()
                .withClient("my-trusted-client")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                .scopes("read", "write", "trust")
                .resourceIds("oauth2-resource")
                .accessTokenValiditySeconds(600)
                .and()

                .withClient("my-client-with-registered-redirect")
                .authorizedGrantTypes("authorization_code")
                .authorities("ROLE_CLIENT")
                .scopes("read", "write")
                .resourceIds("oauth2-resource")
                .redirectUris("http://exmple.com")
                .and()

                .withClient("my-client-with-secret")
                .authorizedGrantTypes("client_credentials", "password")
                .authorities("ROLE_CLIENT")
                .scopes("openid")
                .resourceIds("oauth2-resource")
                .secret("secret");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

                return null;
            }
        });
        endpoints.authenticationManager(authenticationManager);
        //自定义生成token
        /*
        endpoints.tokenServices(new AuthorizationServerTokenServices() {
            @Override
            public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
                return new DefaultOAuth2AccessToken("123243434");
            }

            @Override
            public OAuth2AccessToken refreshAccessToken(String refreshToken, TokenRequest tokenRequest) throws AuthenticationException {
                return null;
            }

            @Override
            public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
                return new DefaultOAuth2AccessToken("4154545-45454-4545545");
            }
        });
        */
        endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory));
    }
}