package com.mychatgpt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
@MapperScan("com.mychatgpt.mapper")
public class MychatgptApplication {

    public static void main(String[] args) {
        SpringApplication.run (MychatgptApplication.class, args);
    }

}
