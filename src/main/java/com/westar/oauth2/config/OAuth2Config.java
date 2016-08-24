package com.westar.oauth2.config;

import com.westar.web.service.UserPressDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanganbang on 7/25/16.
 */
@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Resource
    private InMemoryClientDetailsService memoryClientDetailsService;
    @Resource
    private UserPressDetailsService userPressDetailsService;
    @Resource(name = "jedisConnectionFactory")
    private JedisConnectionFactory jedisConnectionFactory;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //oauth2认证包括两个部分认证， 一般来说都会让用户界别和客户端级别都进行认证之后才会获得最终token，只有单纯的客户端模式的时候不需要i用户级别认证
        BaseClientDetails clientDetails = new BaseClientDetails(
                "my-trusted-config",
                "oauth2-resource",
                "read,write,trust",
                "password,authorization_code,refresh_token,implicit,client_credentials",
                "ROLE_CLIENT,ROLE_TRUSTED_CLIENT"
        );
        clientDetails.setClientSecret("password");
        clientDetails.setAccessTokenValiditySeconds(64200);

        Map<String, ClientDetails> map = new HashMap<>();
        map.put("my-trusted-config", clientDetails);
        memoryClientDetailsService.setClientDetailsStore(map);
        clients.withClientDetails(memoryClientDetailsService);

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.tokenStore(new RedisTokenStore(jedisConnectionFactory));
        endpoints.userDetailsService(userPressDetailsService);
        endpoints.setClientDetailsService(memoryClientDetailsService);

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
    }
}