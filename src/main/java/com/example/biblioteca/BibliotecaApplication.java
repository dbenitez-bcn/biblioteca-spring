package com.example.biblioteca;

import com.auth0.jwt.JWT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BibliotecaApplication {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWT jwt() {
        return new JWT();
    }

    public static void main(String[] args) {
        SpringApplication.run(BibliotecaApplication.class, args);
    }

}
