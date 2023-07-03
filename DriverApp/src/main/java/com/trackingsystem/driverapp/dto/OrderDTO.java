package com.trackingsystem.driverapp.dto;

import com.trackingsystem.driverapp.domain.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String orderID;
    private List<HistoryCoordinateDTO> historyCoordinateDTO;

    private CoordinateDTO start;
    private CoordinateDTO end;
    private Route route;


}
