package com.dorothy.flightops.controller;

import com.dorothy.flightops.dto.FlightAvailabilityResponse;
import com.dorothy.flightops.dto.FlightRequestDTO;
import com.dorothy.flightops.dto.FlightResponseDTO;
import com.dorothy.flightops.model.FlightStatus;
import com.dorothy.flightops.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FlightResponseDTO createFlight(@RequestBody FlightRequestDTO flight) {
        return flightService.createFlight(flight);
    }

    @GetMapping("/{flightNumber}")
    public FlightResponseDTO getFlight(@PathVariable String flightNumber) {
        return flightService.getFlightByNumber(flightNumber);
    }

    @GetMapping("/all")
    public List<FlightResponseDTO> getAllFlights() {
        return flightService.getAllFlights();
    }

    @PutMapping("/{flightNumber}")
    public FlightResponseDTO updateFlight(@PathVariable String flightNumber, @RequestBody FlightRequestDTO flight) {
        return flightService.updateFlight(flightNumber, flight);
    }

    @DeleteMapping("/{flightNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFlight(@PathVariable String flightNumber) {
        flightService.deleteFlight(flightNumber);
    }

    @GetMapping
    public Page<FlightResponseDTO> getFlights(@RequestParam(required = false) String departureStation, @RequestParam(required = false) FlightStatus status,
                                              @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return flightService.getFlights(departureStation, status, page, size);
    }

    @GetMapping("/{id}/availability")
    public FlightAvailabilityResponse checkAvailability(@PathVariable Long id) {
        return flightService.checkAvailability(id);
    }

}
