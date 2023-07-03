package com.trackingsystem.driverapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {


    @Bean
    ModelMapper modelMapper() {
        // Maps any object to another object of similar structure such as Entity to Response
        return new ModelMapper();
    }

}
