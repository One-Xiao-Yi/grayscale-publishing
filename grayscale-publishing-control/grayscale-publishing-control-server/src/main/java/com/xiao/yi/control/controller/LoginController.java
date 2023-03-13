package com.xiao.yi.control.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.xiao.yi.control.entry.LoginParam;
import com.xiao.yi.control.model.reponse.ResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaoyi
 * @since 2023/2/16
 */
@RestController
public class LoginController {

    @Value("${login.default.username:admin}")
    private String username;

    @Value("${login.default.password:admin}")
    private String password;

    @PostMapping("/login")
    public ResponseModel<String> login(@RequestBody LoginParam loginParam, HttpServletRequest request) {
        if (StrUtil.isEmpty(loginParam.getUsername()) || StrUtil.isEmpty(loginParam.getPassword())) {
            return ResponseModel.error("账号或密码为空");
        }
        if (loginParam.getUsername().equals(username) && loginParam.getPassword().equals(password)) {
            String clientIp = ServletUtil.getClientIP(request);
            return ResponseModel.success(clientIp);
        }
        return ResponseModel.error("账号或密码错误");
    }

}
