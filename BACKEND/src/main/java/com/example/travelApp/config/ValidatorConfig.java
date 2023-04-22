package com.example.travelApp.config;

import com.example.travelApp.repositories.UserRepository;
import com.example.travelApp.validation.UniqueEmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {
    @Autowired
    private UserRepository userRepository;

    @Bean
    public UniqueEmailValidator uniqueEmailValidator() {
        return new UniqueEmailValidator(userRepository);
    }
}
