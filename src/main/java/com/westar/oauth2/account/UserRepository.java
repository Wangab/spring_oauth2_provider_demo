package com.westar.oauth2.account;

/**
 * Created by wanganbang on 7/28/16.
 */

public interface UserRepository {

    public User FindUserByUserName(String userName);

    public User addUser(User user);
}
