package com.cw.community.service;

import com.cw.community.mapper.UserMapper;
import com.cw.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by coffeecw 2019/09/21
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        //根据github的accountId查看数据库是否含有该用户
        User dbUser = userMapper.findByAccount(user.getAccountId());
        if(dbUser==null){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            //更新
            userMapper.update(dbUser);
        }
    }
}
