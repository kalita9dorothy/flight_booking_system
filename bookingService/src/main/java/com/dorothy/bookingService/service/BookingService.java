package com.dorothy.bookingService.service;

import com.dorothy.bookingService.dto.BookingCreateRequestDTO;
import com.dorothy.bookingService.dto.BookingStatusResponseDTO;
import com.dorothy.bookingService.event.BookingCreatedEvent;
import com.dorothy.bookingService.model.Booking;
import com.dorothy.bookingService.model.BookingStatus;
import com.dorothy.bookingService.repository.BookingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepo bookingRepository;
    private final ApplicationEventPublisher eventPublisher;

    public Booking createBooking(BookingCreateRequestDTO request) {

        Booking booking = Booking.builder().flightId(request.getFlightId())
                        .userEmail(request.getUserEmail()).seats(request.getSeats())
                        .status(BookingStatus.CREATED).retryCount(0).maxRetries(3).build();
        bookingRepository.save(booking);

        eventPublisher.publishEvent(new BookingCreatedEvent(booking));

        booking.setStatus(BookingStatus.NOTIFICATION_PENDING);
        bookingRepository.save(booking);

        return booking;
    }

    public BookingStatusResponseDTO getBookingStatus(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        String message;

        switch (booking.getStatus()) {
            case CONFIRMED -> message = "Booking confirmed";
            case FAILED -> message = "Booking failed. Please retry";
            case NOTIFICATION_PENDING -> message = "Processing booking";
            default -> message = "Booking in progress";
        }

        return BookingStatusResponseDTO.builder()
                .bookingId(booking.getId())
                .status(booking.getStatus())
                .retryCount(booking.getRetryCount())
                .message(message)
                .build();
    }
}
