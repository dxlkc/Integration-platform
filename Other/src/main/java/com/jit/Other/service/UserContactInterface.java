package com.jit.Other.service;

public interface UserContactInterface {
    //邮件
    String sendMail(String title, String content, String email);

    //
    String get_email_code(String email_id);
}