package com.dorothy.bookingService.dto;

import com.dorothy.bookingService.model.BookingStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingStatusResponseDTO {

    private Long bookingId;
    private BookingStatus status;
    private int retryCount;
    private String message;

}
