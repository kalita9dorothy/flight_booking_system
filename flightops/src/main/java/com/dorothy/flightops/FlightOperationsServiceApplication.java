package com.dorothy.flightops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FlightOperationsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightOperationsServiceApplication.class, args);
	}

}
