package com.lmj;

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

        Object userServiceImpl = context.getBean("userServiceImpl");
        Object userServiceImp2 = context.getBean("userServiceImpl");
        Object userServiceImp3 = context.getBean("userServiceImpl");
        System.out.println("userServiceImpl=========="+userServiceImpl);
        System.out.println("userServiceImp2=========="+userServiceImp2);
        System.out.println("userServiceImp3=========="+userServiceImp3);

    }
}
