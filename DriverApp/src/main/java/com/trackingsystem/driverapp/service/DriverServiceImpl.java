package com.trackingsystem.driverapp.service;

import com.trackingsystem.driverapp.domain.Driver;
import com.trackingsystem.driverapp.domain.Order;
import com.trackingsystem.driverapp.dto.DriverDTO;
import com.trackingsystem.driverapp.dto.DriverRequest;
import com.trackingsystem.driverapp.dto.HistoryCoordinateDTO;
import com.trackingsystem.driverapp.dto.OrderDTO;
import com.trackingsystem.driverapp.kafka.KafkaProducer;
import com.trackingsystem.driverapp.repository.DriverRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    KafkaProducer kafkaProducer;
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
    public DriverDTO updateHistoryCoordinate(String username, OrderDTO orderDTO) throws AccountNotFoundException {
        Driver driver = driverRepository.findByUsername(username).orElseThrow(AccountNotFoundException::new);
        Order order = modelMapper.map(orderDTO, Order.class);
        List<HistoryCoordinateDTO> historyCoordinateDTOs = orderDTO.getHistoryCoordinateDTO();
        List<Order> orders = driver.getOrders();
        for (Order o : orders) {
            o.getHistoryCoordinate().add()
        }


        driverRepository.save(driver);
        DriverDTO driverDTO = modelMapper.map(driver, DriverDTO.class);
        return driverDTO;
    }

    @Override
    public OrderDTO getOrderByID(String username, String orderID) throws AccountNotFoundException {
        Optional<Driver> orderOptional = driverRepository.findOrderByID(username, orderID);
        Driver driver = orderOptional.orElseThrow(AccountNotFoundException::new);
        List<Order> orders = driver.getOrders();

        OrderDTO orderDTO = new OrderDTO();
        for (Order ord : orders) {
            if (ord.getOrderID() != null && ord.getOrderID().equals(orderID))
                orderDTO = modelMapper.map(ord, OrderDTO.class);
            ;
        }
        return orderDTO;
    }

    @Override
    public DriverRequest sendCoordinate(String username, DriverRequest driverRequest) {
        try {
            String orderID = driverRequest.getOrderID();
            if (!orderID.equals(null)) {
                OrderDTO orderDTO = getOrderByID(username, orderID);
                updateHistoryCoordinate(username, orderDTO);
            }
            kafkaProducer.sendMessage(driverRequest);
            return driverRequest;
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
