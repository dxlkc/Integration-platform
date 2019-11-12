package com.jit.Other.controller;


import com.jit.Other.service.UserContactInterface;
import com.jit.Other.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/user")
@RestController
@CrossOrigin
@Log4j2
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private UserContactInterface userContactInterface;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/reg")
    public String reg(@RequestParam("name") String name, @RequestParam String password,
                      @RequestParam String email_id, @RequestParam String email_code,
                      HttpServletResponse response) {

        log.info(name+ " user has register");
        return userService.register(name, password, email_id, email_code);
    }

    @PostMapping("/login")
    public String login(@RequestParam String name,@RequestParam String password) {
        log.info(name+ " user has login");
        return userService.login(name, password);
    }


    @PostMapping("/get_email_code")
    public String get_email_code(@RequestParam String email_id){
//      return userService.get_email_code(email_id);
        log.info(email_id +  " get email code");
        return userContactInterface.get_email_code(email_id);

    }

    @PostMapping("/change_password")
    public String change_password(@RequestParam String name,@RequestParam String password) {
        log.info(name+ " user has changed password");
        userService.changePassword(name, password);
        return "success";
    }

    @PostMapping("/change_forget_passWord")
    public String change_forget_passWord(@RequestParam String email_id,@RequestParam String email_code,
                                  @RequestParam String userName,@RequestParam String new_password,
                                  HttpServletResponse response){
        log.info(userName+"forget his password and changed his password");
        return userService.change_forget_passWord(email_id,email_code,new_password,userName);
    }

    //test
    @PostMapping("/test")
    public String sayTest() {
        System.out.println("test");
        return "test";
    }
}

