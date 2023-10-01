package com.epam.esm.config;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Random;

@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class DataInitializationConfig {

    @Bean
    public Faker javaFaker() {
        return new Faker(random());
    }

    @Bean
    public Random random() {
        return new Random();
    }

}
