package com.trackingsystem.driverapp.controller;

import com.trackingsystem.driverapp.config.ApplicationProperties;
import com.trackingsystem.driverapp.dto.DriverDTO;
import com.trackingsystem.driverapp.dto.DriverRequest;
import com.trackingsystem.driverapp.dto.OrderDTO;
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
    DriverService driverService;

    @PostMapping("/{username}/coordinates")
    public ResponseEntity<?> sendCoordinate(@PathVariable String username, @RequestBody DriverRequest driverRequest) {
        return ResponseEntity.ok(driverService.sendCoordinate(username, driverRequest));
    }


    @PostMapping
    public ResponseEntity<?> saveDriver(@RequestBody DriverDTO driverDTO) {
        return ResponseEntity.ok(driverService.save(driverDTO));
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> addOrder(@PathVariable String username, @RequestBody OrderDTO orderDTO) throws AccountNotFoundException {
        return ResponseEntity.ok(driverService.updateOrderHistory(username, orderDTO));
    }
}
