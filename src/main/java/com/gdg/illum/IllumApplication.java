package com.gdg.illum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gdg.illum")
public class IllumApplication {

    public static void main(String[] args) {
        SpringApplication.run(IllumApplication.class, args);
    }

}
