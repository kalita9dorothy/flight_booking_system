package com.dorothy.bookingService.controller;

import com.dorothy.bookingService.dto.BookingCreateRequestDTO;
import com.dorothy.bookingService.dto.BookingResponseDTO;
import com.dorothy.bookingService.dto.BookingStatusResponseDTO;
import com.dorothy.bookingService.model.Booking;
import com.dorothy.bookingService.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingResponseDTO createBooking(@RequestBody @Valid BookingCreateRequestDTO request){

        Booking booking = bookingService.createBooking(request);

        return BookingResponseDTO.builder().bookingId(booking.getId()).status(booking.getStatus())
                .message("Booking request accepted").build();
    }

    @GetMapping("/{bookingId}")
    public BookingStatusResponseDTO getBookingStatus(@PathVariable Long bookingId){
        return bookingService.getBookingStatus(bookingId);
    }
}
