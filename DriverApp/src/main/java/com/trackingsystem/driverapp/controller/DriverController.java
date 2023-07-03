package com.trackingsystem.driverapp.controller;

import com.trackingsystem.driverapp.config.ApplicationProperties;
import com.trackingsystem.driverapp.dto.DriverDTO;
import com.trackingsystem.driverapp.dto.DriverRequest;
import com.trackingsystem.driverapp.dto.OrderDTO;
import com.trackingsystem.driverapp.kafka.KafkaProducer;
import com.trackingsystem.driverapp.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping(value = "/drivers")
public class DriverController {

    @Autowired
    ApplicationProperties properties;

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    DriverService driverService;

    @PostMapping("/{username}/coordinates")
    public ResponseEntity<?> sendCoordinate(@PathVariable String username, @RequestBody DriverRequest driverRequest) {
        try {
            String orderID = driverRequest.getOrderID();
            if (!orderID.equals(null)) {
                OrderDTO orderDTO = driverService.getOrderByID(username, orderID);
                driverService.updateOrderHistory(username, orderDTO);
            }
            OrderDTO orderDTO = driverService.getOrderByID(username, orderID);
            kafkaProducer.sendMessage(driverRequest);
            return ResponseEntity.ok(driverRequest);
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping
    public ResponseEntity<?> saveDriver(@RequestBody DriverDTO driverDTO) {
        return ResponseEntity.ok(driverService.save(driverDTO));
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateCoordinate(@PathVariable String username, @RequestBody OrderDTO orderDTO) throws AccountNotFoundException {
        return ResponseEntity.ok(driverService.updateOrderHistory(username, orderDTO));
    }


}
