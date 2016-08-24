package com.westar.web.service;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * Created by wanganbang on 8/3/16.
 */
@Service
public class TokenService {

    @Resource(name = "jedisConnectionFactory")
    private JedisConnectionFactory jedisConnectionFactory;

    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return new RedisTokenStore(jedisConnectionFactory).findTokensByClientId(clientId);
    }

}
