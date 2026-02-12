package com.dorothy.bookingService.service;

import com.dorothy.bookingService.dto.FlightAvailabilityResponse;
import com.dorothy.bookingService.exception.FlightNotFoundException;
import com.dorothy.bookingService.exception.FlightServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class FlightOpsClient {

    private final RestTemplate restTemplate;

    @Value("${flightops.base-url}")
    private String flightOpsBaseUrl;

    public FlightAvailabilityResponse checkFlightAvailability(Long flightId) {

        String url = flightOpsBaseUrl + "/api/flights/" + flightId + "/availability";

        try {
            return restTemplate.getForObject(url, FlightAvailabilityResponse.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new FlightNotFoundException("Flight not found with id: " + flightId);
        } catch (ResourceAccessException ex) {
            throw new FlightServiceUnavailableException("Flight service is unavailable");
        }
    }
}