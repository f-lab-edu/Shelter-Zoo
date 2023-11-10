package com.noint.shelterzoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ShelterZooApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShelterZooApplication.class, args);
    }

}
