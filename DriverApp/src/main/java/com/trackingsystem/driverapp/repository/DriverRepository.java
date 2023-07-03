package com.trackingsystem.driverapp.repository;

import com.trackingsystem.driverapp.domain.Driver;
import com.trackingsystem.driverapp.domain.Order;
import com.trackingsystem.driverapp.dto.DriverDTO;
import com.trackingsystem.driverapp.dto.OrderDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DriverRepository extends MongoRepository<Driver, String> {


    Optional<Driver> findByUsername(String username);


    @Query("{'username': :#{#username}, 'orders.orderID': :#{#orderID} }, {'orders.$': 1}")
    Optional<Order> findOrderByID(@Param("username") String username, @Param("orderID") String orderID);

}
