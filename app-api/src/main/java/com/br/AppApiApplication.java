package com.br;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.br")
public class AppApiApplication {

    static void main(String[] args) {
        SpringApplication.run(AppApiApplication.class, args);
    }

}
