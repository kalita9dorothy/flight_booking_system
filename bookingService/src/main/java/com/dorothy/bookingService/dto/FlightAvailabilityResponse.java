package com.dorothy.bookingService.dto;

import lombok.Data;

@Data
public class FlightAvailabilityResponse {

    private Long flightId;
    private String status;
}
