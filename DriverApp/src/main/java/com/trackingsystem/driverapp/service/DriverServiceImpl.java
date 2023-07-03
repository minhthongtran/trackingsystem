package com.trackingsystem.driverapp.service;

import com.trackingsystem.driverapp.domain.Driver;
import com.trackingsystem.driverapp.domain.Order;
import com.trackingsystem.driverapp.dto.DriverDTO;
import com.trackingsystem.driverapp.dto.OrderDTO;
import com.trackingsystem.driverapp.repository.DriverRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Driver save(DriverDTO driverDTO) {
        Driver driver = modelMapper.map(driverDTO, Driver.class);
        System.out.println(driverDTO.getOrders().get(0).getOrderID());
        return driverRepository.save(driver);
    }

    @Override
    public DriverDTO updateOrderHistory( String username, OrderDTO orderDTO) throws AccountNotFoundException {
        Driver driver = driverRepository.findByUsername(username).orElseThrow(AccountNotFoundException::new);
        Order order = modelMapper.map(orderDTO, Order.class);
        driver.getOrders().add(order);
        driverRepository.save(driver);
        DriverDTO driverDTO = modelMapper.map(driver, DriverDTO.class);
        return driverDTO;
    }

    @Override
    public OrderDTO getOrderByID(String username, String orderID) throws AccountNotFoundException {
        Optional<Order> orderOptional = driverRepository.findOrderByID(username, orderID);

//        DriverDTO driverDTO = driverRepository.findOrderByID(username,orderID).;
        Order order = orderOptional.orElseThrow(AccountNotFoundException::new);
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        return orderDTO;
    }


}
