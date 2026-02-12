package com.dorothy.flightops.service;

import com.dorothy.flightops.dto.FlightAvailabilityResponse;
import com.dorothy.flightops.dto.FlightRequestDTO;
import com.dorothy.flightops.dto.FlightResponseDTO;
import com.dorothy.flightops.exception.FlightAlreadyExistsException;
import com.dorothy.flightops.exception.FlightNotFoundException;
import com.dorothy.flightops.model.Flight;
import com.dorothy.flightops.model.FlightStatus;
import com.dorothy.flightops.repository.FlightRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService{

    private final FlightRepo flightRepository;

    @Override
    public FlightResponseDTO createFlight(FlightRequestDTO request) {

        if (flightRepository.findByFlightNumberAndDeletedFalse(request.getFlightNumber()).isPresent()) {
            throw new FlightAlreadyExistsException("Flight already exists with number: " + request.getFlightNumber());
        }

        Flight flight = Flight.builder()
                .flightNumber(request.getFlightNumber())
                .departureStation(request.getDepartureStation())
                .arrivalStation(request.getArrivalStation())
                .std(request.getStd())
                .sta(request.getSta())
                .status(request.getStatus())
                .deleted(false)
                .build();

        return mapToResponse(flightRepository.save(flight));
    }

    @Override
    public FlightResponseDTO getFlightByNumber(String flightNumber) {

        Flight flight = flightRepository.findByFlightNumberAndDeletedFalse(flightNumber)
                        .orElseThrow(() -> new FlightNotFoundException("Flight not found with number" + flightNumber));
        return mapToResponse(flight);
    }

    @Override
    public List<FlightResponseDTO> getAllFlights() {
        return flightRepository.findAllByDeletedFalse().stream().map(this::mapToResponse).toList();
    }

    @Override
    public FlightResponseDTO updateFlight(String flightNumber, FlightRequestDTO updatedFlightRequest) {
        Flight existing = flightRepository.findByFlightNumberAndDeletedFalse(flightNumber)
                            .orElseThrow(() -> new FlightNotFoundException("Flight not found with number" + flightNumber));

        existing.setDepartureStation(updatedFlightRequest.getDepartureStation());
        existing.setArrivalStation(updatedFlightRequest.getArrivalStation());
        existing.setStd(updatedFlightRequest.getStd());
        existing.setSta(updatedFlightRequest.getSta());
        existing.setStatus(updatedFlightRequest.getStatus());

        return mapToResponse(flightRepository.save(existing));
    }

    @Override
    public void deleteFlight(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumberAndDeletedFalse(flightNumber)
                        .orElseThrow(() -> new FlightNotFoundException("Flight not found with number" + flightNumber));
        flight.setDeleted(true);
        flightRepository.save(flight);
    }

    private FlightResponseDTO mapToResponse(Flight flight) {
        return FlightResponseDTO.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .departureStation(flight.getDepartureStation())
                .arrivalStation(flight.getArrivalStation())
                .std(flight.getStd())
                .sta(flight.getSta())
                .status(flight.getStatus())
                .build();
    }

    @Override
    public Page<FlightResponseDTO> getFlights(String departureStation, FlightStatus status, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        //Pageable pageable = PageRequest.of(page, size, Sort.by("std").descending());

        Page<Flight> flightsPage;

        if (departureStation != null && status != null) {
            flightsPage = flightRepository.findByDepartureStationAndStatusAndDeletedFalse(departureStation, status, pageable);
        } else if (departureStation != null) {
            flightsPage = flightRepository.findByDepartureStationAndDeletedFalse(departureStation, pageable);
        } else if (status != null) {
            flightsPage = flightRepository.findByStatusAndDeletedFalse(status, pageable);
        } else {
            flightsPage = flightRepository.findByDeletedFalse(pageable);
        }

        return flightsPage.map(this::mapToResponse);
    }

    @Override
    public FlightAvailabilityResponse checkAvailability(Long flightId) {

        Flight flight = flightRepository.findByIdAndDeletedFalse(flightId).orElseThrow(() -> new FlightNotFoundException("Flight not found with id: " + flightId));

        return new FlightAvailabilityResponse(flight.getId(), flight.getStatus());
    }

}
