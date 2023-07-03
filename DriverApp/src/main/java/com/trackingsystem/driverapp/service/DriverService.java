package com.trackingsystem.driverapp.service;

import com.trackingsystem.driverapp.domain.Driver;
import com.trackingsystem.driverapp.dto.DriverDTO;
import com.trackingsystem.driverapp.dto.OrderDTO;

import javax.security.auth.login.AccountNotFoundException;

public interface DriverService {

     Driver save(DriverDTO driverDTO);

     DriverDTO updateOrderHistory(String username, OrderDTO orderDTO) throws AccountNotFoundException;

     OrderDTO getOrderByID(String username, String orderID) throws AccountNotFoundException;





}
