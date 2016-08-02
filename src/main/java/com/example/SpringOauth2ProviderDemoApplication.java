package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer
public class SpringOauth2ProviderDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringOauth2ProviderDemoApplication.class, args);
    }

    @Bean(name = "redisConnectionFactory")
    RedisConnectionFactory getRedisConnectionFactory(){
        return new JedisConnectionFactory();
    }
}
