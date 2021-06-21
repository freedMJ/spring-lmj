package com.lmj;

import com.lmj.service.UserServiceImpl;
import com.spring.AppConfig;
import com.spring.MySpringApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        //1、从容器类中根据配置文件类得到bean信息
        MySpringApplicationContext context = new MySpringApplicationContext(AppConfig.class);

        UserServiceImpl userServiceImpl = (UserServiceImpl)context.getBean("userServiceImpl");


    }
}
