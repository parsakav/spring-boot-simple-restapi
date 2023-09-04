package com.pgu.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class Application {

    public static void main(String[] args) {
        System.out.println("Hiiii");
       SpringApplication.run(Application.class, args);
    }

}
