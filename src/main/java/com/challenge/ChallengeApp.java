package com.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChallengeApp {
    public static void main(String[] args) {
        SpringApplication.run(ChallengeApp.class, args);
    }
}
