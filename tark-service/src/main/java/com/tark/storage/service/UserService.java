package com.tark.storage.service;

import com.tark.storage.mapper.UserInfoMapper;
import com.tark.storage.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jh on 2017/5/4.
 */
@Service
public class UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfo findUserById(Integer userId) {
       return userInfoMapper.selectByPrimaryKey(userId);
    }

    public UserInfo findUserByName(String username) {
        return userInfoMapper.selectUserByName(username);
    }
}
