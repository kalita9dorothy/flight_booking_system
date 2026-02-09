package com.dorothy.flightops.dto;

import com.dorothy.flightops.model.FlightStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightRequestDTO {

    @NotBlank
    private String flightNumber;

    @NotBlank
    private String departureStation;

    @NotBlank
    private String arrivalStation;

    @NotNull
    @Future
    private LocalDateTime std;

    @NotNull
    @Future
    private LocalDateTime sta;

    @NotNull
    private FlightStatus status;
}
