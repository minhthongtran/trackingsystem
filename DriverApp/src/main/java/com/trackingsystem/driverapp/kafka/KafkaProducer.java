package com.trackingsystem.driverapp.kafka;

import com.google.gson.Gson;
import com.trackingsystem.driverapp.config.ApplicationProperties;
import com.trackingsystem.driverapp.domain.Driver;
import com.trackingsystem.driverapp.dto.DriverDTO;
import com.trackingsystem.driverapp.dto.DriverRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(DriverRequest driverRequest) {
        String message = (new Gson()).toJson(driverRequest);
        kafkaTemplate.send(applicationProperties.getStreamInput(), message)
                .whenComplete((result, exception) -> {
                    if (null == exception)
                        System.out.println("Message sent to topic: {} " + message);
                    else
                        System.out.println("Fail to send message" + exception);
                });
    }

}
