package com.dorothy.bookingService.model;

public enum BookingStatus {

    CREATED,                //Booking Request Received
    NOTIFICATION_PENDING,   //Event published, async work pending
    NOTIFIED,               //Notification successfully sent
    FAILED,                 //Async processing failed
    CANCELLED,              //User/system cancelled
    PENDING,
    CONFIRMED               //Booking fully completed
}
