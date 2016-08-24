package com.westar.web.service;

import com.westar.oauth2.account.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanganbang on 8/3/16.
 */
@Service
public class UserPressDetailsService implements UserDetailsService {

    @Resource(name = "userDAO")
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.westar.oauth2.account.User user = userRepository.FindUserByUserName(s);
        if (user != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            return new User(user.getUsername(), user.getPassword(), authorities);
        } else {
            return null;
        }

    }

    public com.westar.oauth2.account.User saveUser(com.westar.oauth2.account.User user){
        return userRepository.addUser(user);
    }
}
