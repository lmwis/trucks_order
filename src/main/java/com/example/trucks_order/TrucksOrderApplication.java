package com.example.trucks_order;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
@MapperScan("com.example.trucks_order.mapper")
public class TrucksOrderApplication {

	private static final Logger log  = LoggerFactory.getLogger(TrucksOrderApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TrucksOrderApplication.class, args);
	}



}
