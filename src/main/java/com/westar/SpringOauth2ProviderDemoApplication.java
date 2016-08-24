package com.westar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableAuthorizationServer
@EnableSwagger2
public class SpringOauth2ProviderDemoApplication {
    @Bean
    public Docket testOAuthApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("test")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(regex("/user.*"))//过滤的接口
                .build()
                .apiInfo(new ApiInfo(
                        "Oauth2认证服务接口",
                        "Oauth2认证服务接口",
                        "1.0",
                        "NO terms of service",
                        new Contact("王安邦","", ""),
                        "westar project",
                        "https://github.com/Wangab"));
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringOauth2ProviderDemoApplication.class, args);
    }

    @Bean(name = "jedisConnectionFactory")
    JedisConnectionFactory getJedisConnectionFactory(){
        return new JedisConnectionFactory();
    }
    @Bean(name = "memoryClientDetailsService")
    InMemoryClientDetailsService getClientDetailsService(){
        return new InMemoryClientDetailsService();
    }
}
