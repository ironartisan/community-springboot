package com.halo.blog.service;

import com.halo.blog.mapper.UserMapper;
import com.halo.blog.model.User;
import com.halo.blog.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by halo on 2019/8/8.
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);

        }else{
            //更新
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setToken(user.getToken());
            updateUser.setName(user.getName());
            updateUser.setGmtCreate(System.currentTimeMillis());
            updateUser.setGmtModified(user.getGmtCreate());
            updateUser.setAvatarUrl(user.getAvatarUrl());

            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        }

    }
}
