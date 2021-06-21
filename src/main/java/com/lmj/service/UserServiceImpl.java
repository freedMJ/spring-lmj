package com.lmj.service;


import com.spring.BeanNameAware;
import com.spring.annotation.Autowried;
import com.spring.annotation.Component;
import com.spring.interfaces.InitializingBean;

@Component("userServiceImpl")
public class UserServiceImpl implements BeanNameAware , InitializingBean {


    @Autowried("orderServiceImpl")
    private OrderServiceImpl orderServiceImpl;

    private String beanName;


    public void test(){
        System.out.println(this);
        System.out.println("orderService==="+orderServiceImpl);
    }

    public void sayBefo(){
        System.out.println("前置方法执行了。，，，，，");
    }
    public void sayAfter(){
        System.out.println("后置方法执行了。。。。");
    }
    @Override
    public void setBeanName(String beanName) {
        this.beanName=beanName;
    }
    public String getBeanName(){
        return this.beanName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化、、、、、");
    }
}
