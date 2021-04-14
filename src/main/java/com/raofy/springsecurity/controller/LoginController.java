package com.raofy.springsecurity.controller;

import com.raofy.springsecurity.bean.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author raofy
 * @date 2021-04-14 11:24
 * @desc
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping("/failure")
    public BaseResponse loginFailure() {
        return BaseResponse.failure(HttpStatus.UNAUTHORIZED.value(), "登录失败了，老 哥");
    }

    @PostMapping("/success")
    public BaseResponse loginSuccess() {
        // 登录成功后用户的认证信息 UserDetails会存在 安全上下文寄存器 SecurityContextHolder 中
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        return BaseResponse.success("登录成功", username);
    }
}
