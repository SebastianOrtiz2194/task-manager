# Task Manager API Microservice
A resilient and scalable RESTful microservice for managing tasks, built with Java 21 and Spring Boot. This project demonstrates a modern microservice architecture using MongoDB as a primary database, Redis for high-performance caching, and Apache Kafka for asynchronous, event-driven communication.

---

## Architecture Overview
This service follows a decoupled, event-driven architecture designed for performance and scalability.

1. **API Layer (Controller):** Exposes RESTful endpoints for CRUD operations. It delegates all business logic to the service layer.

2. **Service Layer:** Contains the core business logic. It coordinates interactions between the database, cache, and event stream.

3. **Data Layer (Repository):** Uses Spring Data MongoDB for seamless interaction with the persistent database.

4. **Caching Layer (Redis):** Caches frequent read operations (getTaskById) to reduce database latency. The cache is intelligently updated or evicted upon write operations (update, delete).

5. **Event Streaming (Kafka):** On every state change (create, update, delete), the service publishes a TaskEvent to a Kafka topic. This allows other services to subscribe and react to these events in real-time without tightly coupling them to the Task Manager service.

---

## Features
**CRUD Operations:** Full Create, Read, Update, and Delete functionality for tasks.

**High-Performance Caching:** Integrated with Redis to cache responses and improve speed.

**Event-Driven:** Publishes messages to Kafka on data changes.

**Decoupled Logic:** Clear separation of concerns between controllers, services, and repositories.

**Containerized Infrastructure:** Comes with a docker-compose.yml for easy setup of Kafka, Zookeeper, and Redis.

---

## Getting Started
### Prerequisites
- JDK 21

- Apache Maven

- Docker and Docker Compose

---

## Installation & Setup
1. ### Clone the repository:

        git clone <your-repository-url>
        cd task-manager

2. ### Start the infrastructure (Kafka, Zookeeper, Redis):
    Open a terminal in the project root and run:

        docker-compose up -d

    This will start all required services in the background.

3. ### Build and run the Spring Boot application:
        mvn spring-boot:run
    The application will start on http://localhost:8080.

