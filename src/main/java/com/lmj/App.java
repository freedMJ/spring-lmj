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
        UserServiceImpl userServiceImp2 = (UserServiceImpl)context.getBean("userServiceImpl");
        UserServiceImpl userServiceImp3 = (UserServiceImpl)context.getBean("userServiceImpl");
        System.out.println("userServiceImpl=========="+userServiceImpl);
        System.out.println("userServiceImp2=========="+userServiceImp2);
        System.out.println("userServiceImp3=========="+userServiceImp3);
        System.out.println("userServiceImp3====beanname=========="+userServiceImp3.getBeanName());

        userServiceImpl.test();
        userServiceImp2.test();
        userServiceImp3.test();


    }
}
