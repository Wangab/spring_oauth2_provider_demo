package com.example.oauth2.account;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanganbang on 7/28/16.
 */
@Component
public class UserRepository {
    public Map findByUsername(String userName) {
        Map result = new HashMap();
        result.put("username", "user01");
        result.put("password", "123456");

        return result;
    }
}
