package com.westar.oauth2.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by wanganbang on 8/3/16.
 */
public class User {
    @Id
    @ApiModelProperty(hidden = true)
    private String id;
    private String username;
    @ApiModelProperty(hidden = true)
    private String password;
    private List<String> roles;

    @JsonCreator
    public User(@JsonProperty("username") String username, @JsonProperty("pwd") String password, @JsonProperty("roles") List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
