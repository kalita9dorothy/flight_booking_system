**Booking & Notification Service** Standalone service (pre-integration)

A Spring Boot backend service that handles flight bookings using asynchronous event-driven processing, with retry handling and booking status tracking.

This service is designed to simulate real-world backend workflows where downstream operations (like notifications) are handled asynchronously.

**What this project does**

The Booking & Notification Service allows:

* Creating flight bookings

* Processing notifications asynchronously

* Tracking booking status through different stages

* Handling temporary failures using retry tracking

* Exposing booking status APIs for polling

This service is intentionally built as a standalone microservice, independent of flight data.

**Tech Stack**

* Java 17

* Spring Boot 3.5.10

* Spring Data JPA (Hibernate)

* MySQL

* Spring Events

* Spring Async

* Lombok

* Maven

**Project Structure**

booking-notification-service

├── controller

│   └── BookingController.java

├── service

│   └── BookingService.java

├── repository

│   └── BookingRepo.java

├── model

│   ├── Booking.java

│   └── BookingStatus.java

├── dto

│   ├── BookingCreateRequestDTO.java

│   ├── BookingResponseDTO.java

│   └── BookingStatusResponseDTO.java

├── event

│   ├── BookingCreatedEvent.java

│   └── BookingEventListener.java

├── exception

│   └── GlobalExceptionHandler.java

**Key Design Concepts**

1. Event-driven architecture

* Booking creation publishes an internal event
* Notification logic is triggered via application events, keeping booking creation independent from notification processing.
* Improves scalability and maintainability
* Notification handling is implemented inside an event listener to keep the service lightweight; this can be extracted into a separate service if the logic grows.

2. Asynchronous processing

* Notifications are sent using @Async

* Booking creation API returns immediately

* Long-running tasks don’t block API threads

3. Booking status state machine

Each booking moves through states:

    CREATED → NOTIFICATION_PENDING → CONFIRMED


In case of failure:

    NOTIFICATION_PENDING → FAILED


This allows:

* Status polling

* Observability

* Safer async workflows

4. Retry handling (best-effort)

* Failed notification attempts increment retryCount
* Retries happen within async execution
* No scheduler used (intentional design choice)
* Retry attempts are handled within the async execution flow and tracked in the database for observability.

This simulates best-effort retry logic, commonly used when:

* Notifications are non-critical

* Eventual consistency is acceptable

5. Clean API design

* DTO-based request/response

* Entities not exposed directly

* Centralized exception handling

**API Endpoints**
* Create Booking

        POST /api/bookings


Request:

    {
    "flightId": 1,
    "userEmail": "test@gmail.com",
    "seats": 2
    }


Response:

    {
    "bookingId": 1,
    "status": "NOTIFICATION_PENDING"
    }

* Get Booking Status

        GET /api/bookings/{bookingId}


Response:

    {
    "id": 1,
    "status": "CONFIRMED",
    "retryCount": 0
    }

**Configuration**

application.yml

    spring:
        datasource:
            url: jdbc:mysql://localhost:3306/bookingdb
            username: root
            password: "*****"
        jpa:
            hibernate:
                ddl-auto: update
            show-sql: true
            properties:
                hibernate:
                    format_sql: true

**How to Run**

    ./mvnw clean install
    ./mvnw spring-boot:run


**Application runs on:**

    http://localhost:8081

**Testing**

Tested using Postman:

* Booking creation

* Async notification logs

* Retry scenarios

* Booking status polling

* Failure cases

**Future Enhancements**

* Integration with Flight Operations Service

* Persistent retry scheduler

* Message broker (Kafka/RabbitMQ)

* Dead-letter handling

**Author**

Built as part of a backend engineering portfolio to demonstrate:

* Asynchronous processing

* Event-driven design

* Retry handling

* Real-world backend workflows

🔜 Next step

This service will be integrated with Flight Operations Service as part of a microservices communication project.