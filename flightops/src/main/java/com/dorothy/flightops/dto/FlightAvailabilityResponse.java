package com.dorothy.flightops.dto;

import com.dorothy.flightops.model.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightAvailabilityResponse {

    private Long flightId;
    private FlightStatus status;
}
