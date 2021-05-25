package com.example.trucks_order.compent;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 后置处理器
 * 作用就是替换掉bean的默认属性
 * 但spring容器会在启动时把每一个bean分别调用所有的后置处理器的方法，
 * @author lmwis on 2019-04-25 11:12
 */
@Component
public class LmwisCompentBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof LmwisCompent){
            System.out.println("lmwisCompent的后置处理器的before方法");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof LmwisCompent){
            System.out.println("lmwisCompent的后置处理器的after方法");
        }
        return bean;
    }
}
