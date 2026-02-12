package com.dorothy.flightops.service;

import com.dorothy.flightops.dto.FlightAvailabilityResponse;
import com.dorothy.flightops.dto.FlightRequestDTO;
import com.dorothy.flightops.dto.FlightResponseDTO;
import com.dorothy.flightops.model.FlightStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FlightService {

    FlightResponseDTO createFlight(FlightRequestDTO request);

    FlightResponseDTO getFlightByNumber(String flightNumber);

    List<FlightResponseDTO> getAllFlights();

    FlightResponseDTO updateFlight(String flightNumber, FlightRequestDTO updatedFlightRequest);

    void deleteFlight(String flightNumber);

    Page<FlightResponseDTO> getFlights(String departureStation, FlightStatus status, int page, int size);

    FlightAvailabilityResponse checkAvailability(Long flightId);

}
