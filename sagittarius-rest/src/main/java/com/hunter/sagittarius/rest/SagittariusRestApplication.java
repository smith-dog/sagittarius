package com.hunter.sagittarius.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.hunter"})
public class SagittariusRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SagittariusRestApplication.class, args);
    }
}
