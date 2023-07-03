package com.trackingsystem.driverapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String orderID;
    private List<HistoryCoordinate> historyCoordinate;

    private Coordinate start;
    private Coordinate end;
    private Route route;


}
