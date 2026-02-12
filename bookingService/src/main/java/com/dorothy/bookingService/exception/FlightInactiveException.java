package com.dorothy.bookingService.exception;

public class FlightInactiveException extends RuntimeException{

    public FlightInactiveException(String message) {
        super(message);
    }
}
