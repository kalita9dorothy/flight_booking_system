package com.dorothy.bookingService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingCreateRequestDTO {

    @NotNull
    private Long flightId;

    @Email
    @NotNull
    private String userEmail;

    @Min(1)
    private int seats;
}
