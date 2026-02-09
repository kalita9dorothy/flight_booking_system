package com.dorothy.flightops.repository;

import com.dorothy.flightops.model.Flight;
import com.dorothy.flightops.model.FlightStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlightRepo extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightNumberAndDeletedFalse(String flightNumber);

    List<Flight> findAllByDeletedFalse();

    Page<Flight> findByDeletedFalse(Pageable pageable);

    Page<Flight> findByDepartureStationAndDeletedFalse(String departureStation, Pageable pageable);

    Page<Flight> findByStatusAndDeletedFalse(FlightStatus status, Pageable pageable);

    Page<Flight> findByDepartureStationAndStatusAndDeletedFalse(String departureStation, FlightStatus status, Pageable pageable);

}
