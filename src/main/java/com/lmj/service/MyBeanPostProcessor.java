package com.lmj.service;

import com.spring.BeanPostProcessor;
import com.spring.annotation.Component;


@Component("myBeanPostProcessor")
public class MyBeanPostProcessor implements BeanPostProcessor {




    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if("userServiceImpl".equals(beanName)){
            ((UserServiceImpl)bean).sayBefo();
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if("userServiceImpl".equals(beanName)){
            ((UserServiceImpl)bean).sayAfter();
        }
        return null;
    }
}
