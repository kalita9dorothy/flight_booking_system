package com.dorothy.bookingService.repository;

import com.dorothy.bookingService.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepo extends JpaRepository<Booking, Long> {

    Optional<Booking> findById(Long id);
}
