package com.dorothy.bookingService.exception;

public class FlightServiceUnavailableException extends RuntimeException{

    public FlightServiceUnavailableException(String message) {
        super(message);
    }
}
