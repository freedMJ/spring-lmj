package com.spring;


import com.lmj.service.UserServiceImpl;

@ComponentScan("com.lmj.service")
public class AppConfig {


    @Bean
    public UserServiceImpl getUserviceImpl(){
        return new UserServiceImpl();
    }
}
