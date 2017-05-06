package com.tark.storage.web;

import com.tark.storage.common.web.core.ResponseMessage;
import com.tark.storage.common.web.core.ResultStatus;
import com.tark.storage.common.web.core.TokenModel;
import com.tark.storage.model.UserInfo;
import com.tark.storage.security.annotation.Authorization;
import com.tark.storage.security.annotation.CurrentUser;
import com.tark.storage.security.manager.TokenManager;
import com.tark.storage.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jh on 2017/5/4.
 */
@Api(value = "App token 处理试例",description="一个简单的app Token试例和App返回数据格式样例")
@RestController
@RequestMapping("/tokens")
public class TokenController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;

    @PostMapping
    public ResponseEntity<ResponseMessage> login(@RequestParam String username, @RequestParam String password) {
        Assert.notNull(username, "username can not be empty");
        Assert.notNull(password, "password can not be empty");

        UserInfo userInfo = userService.findUserByName(username);

        if(userInfo == null || !password.equals(userInfo.getPassword())) {
            return new ResponseEntity<ResponseMessage>(ResponseMessage.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR), HttpStatus.NOT_FOUND);
        }

        TokenModel model = tokenManager.createToken(userInfo.getId());
        return new ResponseEntity<ResponseMessage>(ResponseMessage.ok(model), HttpStatus.OK);
    }

    @PostMapping("/logout")
    @Authorization
    public ResponseEntity<ResponseMessage> logout(@CurrentUser UserInfo userInfo) {
        tokenManager.deleteToken(userInfo.getId());
        return new ResponseEntity<>(ResponseMessage.ok(), HttpStatus.OK);
    }
}
