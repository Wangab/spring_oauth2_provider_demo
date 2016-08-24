package com.westar.oauth2.account;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by wanganbang on 8/3/16.
 */
@Repository("userDAO")
public class UserRepositoryImpl implements UserRepository{
    @Resource
    private MongoOperations mongoOperations;

    @Override
    public User FindUserByUserName(String userName) {
        return mongoOperations.findOne(new Query(Criteria.where("username").is(userName)), User.class);
    }

    @Override
    public User addUser(User user) {
        mongoOperations.save(user);
        return user;
    }
}
