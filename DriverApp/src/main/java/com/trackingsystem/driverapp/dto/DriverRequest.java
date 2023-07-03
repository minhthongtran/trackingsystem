package com.trackingsystem.driverapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverRequest {

    private String username;
    private CoordinateDTO coordinateDTO;

    private String orderID;
}
