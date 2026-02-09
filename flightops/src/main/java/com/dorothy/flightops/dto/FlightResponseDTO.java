package com.dorothy.flightops.dto;

import com.dorothy.flightops.model.FlightStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FlightResponseDTO {

    private Long id;
    private String flightNumber;
    private String departureStation;
    private String arrivalStation;
    private LocalDateTime std;
    private LocalDateTime sta;
    private FlightStatus status;
}
