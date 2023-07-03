package com.trackingsystem.driverapp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
@Data
public class ApplicationProperties {

    private String applicationId;
    private String username;
    private float lng;
    private float lat;

    private String streamInput;
    private String streamOutput;
    private String bootstrapAddress;

}
