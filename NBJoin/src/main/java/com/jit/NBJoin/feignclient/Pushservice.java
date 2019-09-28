package com.jit.NBJoin.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-push")
public interface Pushservice {

    @PostMapping("/pushdata")
    void push(@RequestParam String msg);
}
