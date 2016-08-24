package com.westar.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by wanganbang on 7/28/16.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user*","/v2/api-docs*","/swagger-ui.*" )
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();

//        http.authorizeRequests()
//                .anyRequest()
//                .fullyAuthenticated()
//                .and()
//
//                .httpBasic()
//                .and()
//
//                .csrf().disable();
    }
}
