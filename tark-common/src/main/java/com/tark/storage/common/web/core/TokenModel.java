package com.tark.storage.common.web.core;

/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 * Created by jh on 2017/5/4.
 */
public class TokenModel {
    //用户id
    private Integer userId;

    //随机生成的uuid
    private String token;

    public TokenModel(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
