package com.jit.Other.service;

public interface UserService {

    //注册
    String register(String name, String password, String email, String email_code);

    //登录
    String login(String name, String password);

    //更改密码
    int changePassword(String name, String password);

    //忘记密码  修改
    String change_forget_passWord(String email_id, String email_code, String new_password, String userName);

}
