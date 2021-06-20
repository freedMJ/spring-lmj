package com.lmj.service;


import com.spring.Autowried;
import com.spring.BeanNameAware;
import com.spring.Component;

@Component("userServiceImpl")
public class UserServiceImpl implements BeanNameAware {


    @Autowried("orderServiceImpl")
    private OrderServiceImpl orderServiceImpl;

    private String beanName;


    public void test(){
        System.out.println(this);
        System.out.println("orderService==="+orderServiceImpl);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName=beanName;
    }
    public String getBeanName(){
        return this.beanName;
    }
}
