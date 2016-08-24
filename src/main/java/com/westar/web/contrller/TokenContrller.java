package com.westar.web.contrller;

import com.westar.web.service.TokenService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by wanganbang on 8/3/16.
 */
@RestController
public class TokenContrller {
    @Resource
    private TokenService tokenService;

    public String getToken(String clientid){

        return "1212121";
    }
}
