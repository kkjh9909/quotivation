package com.example.quotivation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableJpaAuditing
@EnableAspectJAutoProxy
@EnableScheduling
@SpringBootApplication
public class QuotivationApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotivationApplication.class, args);
    }

}
