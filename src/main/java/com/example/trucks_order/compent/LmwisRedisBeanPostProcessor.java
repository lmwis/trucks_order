package com.example.trucks_order.compent;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * bean初始化的后置处理器
 * @author lmwis on 2019-04-25 10:57
 */
@Component
public class LmwisRedisBeanPostProcessor implements BeanPostProcessor {
    /**
     * 初始化之前的调用方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof RedisTemplate){
            RedisTemplate redisTemplate = (RedisTemplate)bean;
            redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer(Object.class));
            return redisTemplate;
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}
