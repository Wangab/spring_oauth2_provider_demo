package com.westar.web.contrller;

import com.westar.oauth2.account.LoginVO;
import com.westar.oauth2.account.User;
import com.westar.web.service.UserPressDetailsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2RequestValidator;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanganbang on 8/3/16.
 */
@RestController
@RequestMapping("/user")
public class UserContrller{
    private OAuth2RequestValidator oAuth2RequestValidator = new DefaultOAuth2RequestValidator();
    @Resource(name = "memoryClientDetailsService")
    private InMemoryClientDetailsService clientDetailsService;

    private DefaultOAuth2RequestFactory defaultOAuth2RequestFactory;
    @Resource
    private AuthorizationServerEndpointsConfiguration authorizationServerEndpointsConfiguration;

    @Resource
    private UserPressDetailsService userService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "用户注册接口", notes = "输入 和输出为JSON格式", produces = "application/json")
    public @ResponseBody User addUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录接口", notes = "输入 和输出为JSON格式", produces = "application/json")
    public @ResponseBody Map<String, Object> login(@RequestBody LoginVO loginVO){
        String clientId = loginVO.getClientid();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id", clientId);
        parameters.put("grant_type", "password");
        parameters.put("username", loginVO.getUsername());
        parameters.put("password", loginVO.getPassword());

        ClientDetails authenticatedClient = clientDetailsService.loadClientByClientId(clientId);
        defaultOAuth2RequestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);
        TokenRequest tokenRequest = defaultOAuth2RequestFactory.createTokenRequest(parameters, authenticatedClient);

        if (clientId != null && !clientId.equals("")) {
            // Only validate the client details if a client authenticated during this
            // request.
            if (!clientId.equals(tokenRequest.getClientId())) {
                // double check to make sure that the client ID in the token request is the same as that in the
                // authenticated client
                throw new InvalidClientException("Given client ID does not match authenticated client");
            }
        }
        if (authenticatedClient != null) {
            oAuth2RequestValidator.validateScope(tokenRequest, authenticatedClient);
        }

        OAuth2AccessToken token = authorizationServerEndpointsConfiguration.getEndpointsConfigurer().getTokenGranter().grant(tokenRequest.getGrantType(), tokenRequest);
        if (token == null) {
            throw new UnsupportedGrantTypeException("Unsupported grant type: " + tokenRequest.getGrantType());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("username", loginVO.getUsername());
        result.put("clientid", loginVO.getClientid());
        result.put("tokeninfo", token);
        return result;
    }
}
