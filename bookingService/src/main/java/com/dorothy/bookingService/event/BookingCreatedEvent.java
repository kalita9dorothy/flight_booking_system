package com.dorothy.bookingService.event;

import com.dorothy.bookingService.model.Booking;

public class BookingCreatedEvent{
    private final Booking booking;

    public BookingCreatedEvent(Booking booking){
        this.booking = booking;
    }

    public Booking getBooking(){
        return booking;
    }
}
