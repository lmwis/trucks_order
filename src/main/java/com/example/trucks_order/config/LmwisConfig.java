package com.example.trucks_order.config;

import com.example.trucks_order.compent.LmwisCompent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lmwis on 2019-04-25 11:10
 */
@Configuration
public class LmwisConfig {

    @Bean(initMethod = "init")
    public LmwisCompent lmwisCompent(){
        return new LmwisCompent();
    }
}
