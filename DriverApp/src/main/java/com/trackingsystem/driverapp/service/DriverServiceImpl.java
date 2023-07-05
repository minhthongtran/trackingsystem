package com.trackingsystem.driverapp.service;

import com.trackingsystem.driverapp.domain.Coordinate;
import com.trackingsystem.driverapp.domain.Driver;
import com.trackingsystem.driverapp.domain.HistoryCoordinate;
import com.trackingsystem.driverapp.domain.Order;
import com.trackingsystem.driverapp.dto.*;
import com.trackingsystem.driverapp.kafka.KafkaProducer;
import com.trackingsystem.driverapp.repository.DriverRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public DriverDTO updateOrderHistory(String username, OrderDTO orderDTO) throws AccountNotFoundException {
        Driver driver = driverRepository.findByUsername(username).orElseThrow(AccountNotFoundException::new);
        Order order = modelMapper.map(orderDTO, Order.class);
        driver.getOrders().add(order);
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
            orderDTO.setHistoryCoordinateDTO(mapToDTO(ord.getHistoryCoordinate()));
            break;
        }
        return orderDTO;
    }

    private List<HistoryCoordinateDTO> mapToDTO(List<HistoryCoordinate> historyCoordinates) {
        List<HistoryCoordinateDTO> historyCoordinateDTOS = new ArrayList<>();
        for (HistoryCoordinate history : historyCoordinates) {

            historyCoordinateDTOS.add(modelMapper.map(history, HistoryCoordinateDTO.class));
        }
        return historyCoordinateDTOS;
    }

    private CoordinateDTO mapToDTO(Coordinate coordinate){
        CoordinateDTO coordinateDTO = modelMapper.map(coordinate, CoordinateDTO.class);
                return coordinateDTO;
    }

    @Override
    public DriverRequest sendCoordinate(String username, DriverRequest driverRequest) {
        try {
            String orderID = driverRequest.getOrderID();
            if (!orderID.equals(null)) {
//                OrderDTO orderDTO = getOrderByID(username, orderID);
                updateHistoryCoordinate(username, driverRequest);
            }
            kafkaProducer.sendMessage(driverRequest);
            return driverRequest;
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DriverDTO updateHistoryCoordinate(String username, DriverRequest driverRequest) throws AccountNotFoundException {
        Driver driver = driverRepository.findByUsername(username).orElseThrow(AccountNotFoundException::new);
        String orderID = driverRequest.getOrderID();
        OrderDTO orderDTO = getOrderByID(username, orderID);

        List<HistoryCoordinateDTO> historyCoordinateDTOs = orderDTO.getHistoryCoordinateDTO();
        historyCoordinateDTOs.add(new HistoryCoordinateDTO(driverRequest.getCoordinateDTO(), LocalDateTime.now()));
        orderDTO.setHistoryCoordinateDTO(historyCoordinateDTOs);
        DriverDTO driverDTO = modelMapper.map(driver, DriverDTO.class);
        driverRepository.save(driver);
        return driverDTO;
    }


}
