package com.dorothy.bookingService.event;

import com.dorothy.bookingService.model.Booking;
import com.dorothy.bookingService.model.BookingStatus;
import com.dorothy.bookingService.repository.BookingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventListener {

    private final BookingRepo bookingRepo;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Async
    @EventListener
    public void handleBookingCreatedEvent(BookingCreatedEvent event) {

        Booking booking = event.getBooking();

        try {
            // Simulate notification sending
            System.out.println("Sending notification to: " + booking.getUserEmail());

            // Simulate possible failure
            if (Math.random() > 0.7) {
                throw new RuntimeException("Temporary notification failure");
            }

            booking.setStatus(BookingStatus.NOTIFIED);
            bookingRepo.save(booking);

            booking.setStatus(BookingStatus.CONFIRMED);
            bookingRepo.save(booking);

            System.out.println("Booking confirmed for: " + booking.getUserEmail());

        } catch (Exception ex) {

            int retries = booking.getRetryCount() + 1;
            booking.setRetryCount(retries);

            if (retries >= booking.getMaxRetries()) {
                booking.setStatus(BookingStatus.FAILED);
                bookingRepo.save(booking);

                System.out.println("Booking FAILED after retries for: " + booking.getUserEmail());
            } else {
                bookingRepo.save(booking);

                System.out.println("Retrying booking notification. Attempt: " + retries);

                // Re-publish event for retry
                applicationEventPublisher.publishEvent(new BookingCreatedEvent(booking));
            }
        }
    }
}
