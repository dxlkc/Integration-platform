package com.jit.Other.service.Impl;


import com.jit.Other.mapper.UserMapper;
import com.jit.Other.redis.RedisUtil;
import com.jit.Other.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisUtil redisUtil;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String register(String name, String password, String email, String email_code) {
        if (redisUtil.get(email).equals(email_code) == false)
            return "wrong email_code";
        if (userMapper.ExistEmail(email) != null)
            return "exist email";
        if (userMapper.ExistUser(name) != null)
            return "exist user";
        userMapper.insertNewUser(name, password);
        userMapper.insertNewUserEmail(name, email);
        return "success";
    }

    @Override
    public String login(String name, String password) {
        if (userMapper.findPasswordByName(name) == null)
            return "no such user";
        if (userMapper.findPasswordByName(name).equals(password))
            return "success";
        return "fail";
    }

    @Override
    public int changePassword(String name, String password) {
        return userMapper.updatePasswordByName(name, password);
    }

    @Override
    public String change_forget_passWord(String email_id, String email_code, String new_password, String userName) {

        System.out.println(redisUtil.get(email_id));

        if (redisUtil.get(email_id).equals(email_code)) {
            userMapper.updatePasswordByName(userName, new_password);
            return "success";
        }
        return "failed";
    }
}
