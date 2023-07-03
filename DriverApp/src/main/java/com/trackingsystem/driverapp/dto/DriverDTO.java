package com.trackingsystem.driverapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDTO {

    private String _id;
    private String username;
    private List<OrderDTO> orders;


}
