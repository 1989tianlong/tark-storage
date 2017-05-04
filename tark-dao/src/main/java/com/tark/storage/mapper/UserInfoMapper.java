package com.tark.storage.mapper;

import com.tark.storage.common.mybatis.MyMapper;
import com.tark.storage.model.UserInfo;

public interface UserInfoMapper extends MyMapper<UserInfo> {
    public UserInfo selectUserByName(String username);

}