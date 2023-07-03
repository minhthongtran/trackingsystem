package com.trackingsystem.driverservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private String applicationId;
    private String username;
    private float lng;
    private float lat;

    private String streamInput;
    private String streamOutput;
    private String bootstrapAddress;
    private String url;


}
