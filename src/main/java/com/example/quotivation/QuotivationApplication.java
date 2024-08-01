package com.example.quotivation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class QuotivationApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotivationApplication.class, args);
    }

}
