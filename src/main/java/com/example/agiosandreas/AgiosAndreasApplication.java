package com.example.agiosandreas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.example.agiosandreas.users")
@SpringBootApplication
public class AgiosAndreasApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgiosAndreasApplication.class, args);
    }

}
