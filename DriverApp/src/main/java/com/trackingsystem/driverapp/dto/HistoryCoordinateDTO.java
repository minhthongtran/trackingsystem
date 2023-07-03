package com.trackingsystem.driverapp.dto;

import com.trackingsystem.driverapp.domain.Coordinate;
import com.trackingsystem.driverapp.domain.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryCoordinateDTO {

    private CoordinateDTO coordinateDTO;
    private LocalDateTime dateTime;


}
