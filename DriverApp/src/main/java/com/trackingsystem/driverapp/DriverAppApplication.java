package com.trackingsystem.driverapp;

import com.trackingsystem.driverapp.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafkaStreams
@EnableConfigurationProperties(ApplicationProperties.class)
public class DriverAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriverAppApplication.class, args);
    }

}
