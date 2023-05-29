package com.mychatgpt.controller;

import com.mychatgpt.service.UserService;
import com.mychatgpt.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;
    @RequestMapping("login")
    public Result login(@RequestParam("code") String code,@RequestParam("rawData") String rawData,@RequestParam String signature){
        return userService.login (code, rawData, signature);
    }
    @RequestMapping("currentUser")
    public Result currentUser(){
        return userService.currentUser();
    }
}
