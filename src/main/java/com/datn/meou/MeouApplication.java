package com.datn.meou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MeouApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeouApplication.class, args);
    }

}
