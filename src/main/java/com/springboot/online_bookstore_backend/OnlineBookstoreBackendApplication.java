package com.springboot.online_bookstore_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class OnlineBookstoreBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookstoreBackendApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder encoding(){
        return new BCryptPasswordEncoder();
    }
}
