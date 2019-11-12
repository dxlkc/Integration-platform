package com.jit.Other.service.Impl;


import com.jit.Other.mapper.UserMapper;
import com.jit.Other.redis.RedisUtil;
import com.jit.Other.service.UserContactInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

@Service
public class UserContactImpl implements UserContactInterface {

    @Autowired
    public MailSender mailSender;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    public UserMapper  userMapper;

    @Value("873168698@qq.com")
    private String from;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public String sendMail(String title, String content, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setSubject(title);
        message.setTo(email);
        message.setText(content);

        try {
            mailSender.send(message);
            logger.info("测试邮件已发送。");
            return "success";

        } catch (Exception e) {
            logger.error("发送邮件时发生异常了！", e);
            return "error";
        }

    }


    @Override
    public String get_email_code(String email_id){
//        if(userMapper.ExistEmail(email_id)==null)
//            return "email not register";
        String vef_code = String.valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
        String  result = sendMail( "验证码",  "你好，您的验证码是： "+ vef_code ,  email_id);
        if(result.equals("success")) {
            redisUtil.del(email_id);
            redisUtil.set(email_id,vef_code,300);//五分钟后清除验证码
        }
        return result;
    }
}
