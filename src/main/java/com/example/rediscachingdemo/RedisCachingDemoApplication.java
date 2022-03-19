package com.example.rediscachingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class RedisCachingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisCachingDemoApplication.class, args);
	}




}
