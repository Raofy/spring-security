package com.raofy.springsecurity.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


import java.util.HashMap;
import java.util.Map;

/**
 * @author raofy
 * @date 2021-04-13 17:34
 * @desc 自定义UserDetailsManager类，实现用户登录认证信息加载，通过的委托的方式进行执行
 */
public class UserDetailsRepository {

    protected final Log logger = LogFactory.getLog(getClass());

    private Map<String, UserDetails> users = new HashMap<>();


    public void createUser(UserDetails user) {
        users.putIfAbsent(user.getUsername(), user);
    }

    public void deleteUser(String username) {
        users.remove(username.toLowerCase());
    }

    public void updateUser(UserDetails user) {
        Assert.isTrue(userExists(user.getUsername()), "用户不存在！");
        users.put(user.getUsername(), user);
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext()
                .getAuthentication();

        if (currentUser == null) {
            // This would indicate bad coding somewhere
            throw new AccessDeniedException("无法更改密码，因为在上下文中找不到当前用户的身份验证对象。");
        }

        String username = currentUser.getName();

        logger.debug("修改密码的用户名为： '" + username + "'");

        UserDetails user = users.get(username);

        if (user == null) {
            throw new IllegalStateException("当前用户不存在于数据库中。");
        }

//        user.setPassword(newPassword);

        // 自定义实现密码更新逻辑



    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserDetails user = users.get(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }
}
