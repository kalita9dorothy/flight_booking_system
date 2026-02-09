**Flight Operations Service** Standalone service (pre-microservice integration)

A Spring Boot REST API for managing flight data, built as a standalone backend service with clean architecture, pagination, filtering, soft delete, and auditing.

This service is designed to represent a core domain microservice in an airline system.

**What this project does**

The Flight Operations Service allows:

* Creating and managing flights
* Searching flights with pagination & filtering
* Soft deleting flights (without losing historical data)
* Tracking audit information automatically

It is intentionally built as an independent service, without coupling to booking or notification logic.
This separation allows the service to evolve independently and be integrated later as part of a microservices architecture.

**Tech Stack**

* Java 17
* Spring Boot 3.5.10
* Spring Data JPA (Hibernate)
* MySQL
* Lombok
* Maven

**Project Structure**

flight-operations-service

├── controller

│   └── FlightController.java

├── service

│   ├── FlightService.java

│   └── FlightServiceImpl.java

├── repository

│   └── FlightRepo.java

├── model

│   ├── Flight.java

│   └── FlightStatus.java

├── dto

│   ├── FlightRequestDTO.java

│   └── FlightResponseDTO.java

├── exception

│   ├── ErrorResponse.java

│   ├── GlobalExceptionHandler.java

│   ├── FlightNotFoundException.java

│   └── FlightAlreadyExistsException.java


**Key Design Decisions**

1.DTO-based API design

* Controllers never expose entities directly
* Clear separation between API contract and persistence model

2.Soft Delete

Instead of deleting records permanently:
* A deleted flag is used
* All read queries filter out deleted records

Why?
* Preserves historical data
* Common enterprise practice

3.Pagination & Filtering

Flights can be queried using:
* Source
* Status
* Page number & size

This prevents:
* Large payloads
* Memory issues
* Inefficient full-table scans

4.Auditing

Automatic tracking of:
* createdAt
* updatedAt

Using Spring Data JPA auditing.

5.Global Exception Handling

All exceptions are handled centrally using:
* @ControllerAdvice

Benefits:
* Clean controllers
* Consistent error responses
* Easy to extend

**API Endpoints**

* Create Flight

    `POST /api/flights`

* Get All Flights (simple)

    `GET /api/flights`

* Get Flights (pagination + filtering)

    `GET /api/flights/search?source=DEL&status=ACTIVE&page=0&size=10`

* Soft Delete Flight

    `DELETE /api/flights/{id}`

**Configuration**

application.yml

    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/flightopdb
        username: root
        password: "****"
      jpa:
        hibernate:
          ddl-auto: update
      show-sql: true
      properties:
        hibernate:
          format_sql: true

Tables are created automatically on application startup.


**How to Run**

    ./mvnw clean install
    ./mvnw spring-boot:run

**Application runs on:**

    http://localhost:8080

**Testing**

* APIs tested using Postman
* Validations, error handling, pagination, and soft delete verified

**Future Enhancements**

* Integration with Booking Service
* Caching for frequently searched routes
* Role-based access control

**Author**

Built as part of a backend engineering portfolio to demonstrate:

* Clean REST design
* Real-world JPA usage
* Enterprise-ready patterns


