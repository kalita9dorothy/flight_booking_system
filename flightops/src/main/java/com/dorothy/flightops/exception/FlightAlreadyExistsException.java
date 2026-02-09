package com.dorothy.flightops.exception;

public class FlightAlreadyExistsException extends RuntimeException{

    public FlightAlreadyExistsException(String message){
        super(message);
    }
}
