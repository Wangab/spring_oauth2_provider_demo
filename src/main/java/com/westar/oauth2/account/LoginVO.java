package com.westar.oauth2.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wanganbang on 8/4/16.
 */
public class LoginVO {
    private String clientid;
    private String username;
    private String password;

    @JsonCreator
    public LoginVO(@JsonProperty("clientid") String clientid, @JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.clientid = clientid;
        this.username = username;
        this.password = password;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
