//package com.example.oauth2.web;
//
//import com.westar.oauth2.account.UserRepository;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by wanganbang on 7/28/16.
// */
////@Configuration
//public class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
//    @Resource
//    private UserRepository userRepository;
//
//    @Override
//    public void init(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(getUserDetailsService());
//    }
//
//    @Bean(name = "userDetailsService")
//    UserDetailsService getUserDetailsService() {
//        return new UserDetailsService() {
//
//            @Override
//            public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//                Map map = userRepository.findByUsername(userName);
//                if (map != null) {
//                    String password = map.get("password").toString();
//                    List<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("USER", "ROLE_CLIENT");
//                    return new User(userName, password, true, true, true, true, authorities);
//                } else {
//                    throw new UsernameNotFoundException("could not find the user [" + userName +"]");
//                }
//            }
//        };
//    }
//}
