package com.example.trucks_order.compent;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author lmwis on 2019-04-25 11:09
 */
public class LmwisCompent implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("调用InitializingBean的afterPropertiesSet方法");
    }

    public LmwisCompent(){
        System.out.println("我是构造方法");
    }
    public void init(){
        System.out.println("我是自定义的初始化方法");
    }
}
