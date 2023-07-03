package com.trackingsystem.driverapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryCoordinate {

    private Coordinate coordinate;
    private LocalDateTime dateTime;


}
