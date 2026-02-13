**Airline Microservices Integration**

This repository demonstrates REST-based communication between two independently deployable Spring Boot microservices:

* Flight Operations Service

* Asynchronous Booking Service

The integration showcases service-to-service validation, domain boundaries, and resilience-aware error handling in a distributed backend system.

**Architecture Overview**

`Client → Booking Service → Flight Service → MySQL`

**Responsibilities**

1. Flight Operations Service

* Manages flight lifecycle
* Supports pagination, filtering, soft delete
* Enforces domain rules at data boundary

2. Booking Service

* Creates bookings asynchronously
* Validates flight eligibility via REST call
* Handles retry logic and booking state transitions

**Inter-Service Communication**

Booking Service validates a flight before creating a booking:

`GET /api/flights/{id}/availability`

Validation Rules:

* Booking allowed only when flight status is SCHEDULED or DELAYED

* Soft-deleted flights behave as non-existent (404)

* Downstream errors are translated into meaningful HTTP responses

No shared database.
No shared entities.
Communication strictly via REST contracts.

**Key Design Decisions**
1. Clear Service Boundaries

Each service owns its data and domain logic.
Soft-deleted flights are not exposed across service boundaries.

2. Asynchronous Processing

Booking creation publishes an internal event.
Notification processing runs asynchronously using @Async.

3. Retry Handling

Failed notification attempts increment retry count.
Designed for eventual consistency without blocking APIs.

4. Error Translation

Downstream failures are converted to:

404 → Flight not found

400 → Booking not allowed

503 → Flight service unavailable

This prevents leaking internal errors to clients.

**Tech Stack**

* Java 17

* Spring Boot 3.x

* Spring Data JPA (Hibernate)

* MySQL

* REST (RestTemplate)

* Spring Async

* Maven

**Ports**

| Service           | Port |
| ----------------- | ---- |
| Flight Operations | 8080 |
| Booking Service   | 8081 |

**How to Run**

Start both services independently:

    cd flight-operations-service
    ./mvnw spring-boot:run
    
    cd booking-notification-service
    ./mvnw spring-boot:run

Ensure MySQL is running and databases exist:

* flightopdb

* bookingdb

**What This Project Demonstrates**

* Microservice-to-microservice REST communication

* Domain-driven validation across services

* Asynchronous workflow handling

* Retry logic and booking state machine

* Soft-delete aware integration

* Production-style exception handling

**Future Enhancements**

* Resilience patterns (timeouts, circuit breaker)

* API Gateway

* Service discovery

* Message broker (Kafka/RabbitMQ)

* Docker Compose setup

**Purpose**

Built to demonstrate real-world microservice integration patterns and distributed backend design as part of a backend engineering portfolio.



