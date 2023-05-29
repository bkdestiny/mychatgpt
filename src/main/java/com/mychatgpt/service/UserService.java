package com.mychatgpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mychatgpt.entity.User;
import com.mychatgpt.mapper.UserMapper;
import com.mychatgpt.util.Result;

public interface UserService extends IService<User>{
    Result login(String code,String rawData,String signature);

    Result currentUser();
}
