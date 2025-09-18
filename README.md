# Task Manager API Microservice
A resilient and scalable RESTful microservice for managing tasks, built with Java 21 and Spring Boot. This project demonstrates a modern microservice architecture using MongoDB as a primary database, Redis for high-performance caching, Apache Kafka for asynchronous, event-driven communication, and JWT auth for security.

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

**Robust Error Handling:** Global exception handling for consistent and clean error responses.

**Input Validation:** Automatic server-side validation of request data.

**High-Performance Caching:** Integrated with Redis to cache responses and improve speed.

**Containerized Infrastructure:** Comes with a docker-compose.yml for easy setup of Kafka, Zookeeper, and Redis.

---

## Getting Started
### Prerequisites
- JDK 21

- Apache Maven

- Docker and Docker Compose

---

## Configuration
Key configuration properties are located in `src/main/resources/application.properties`.

- `server.port`: The port on which the application runs.
- `spring.data.mongodb.uri`: Connection string for the MongoDB database.
- `spring.data.redis.host` / `port`: Connection details for the Redis cache.
- `spring.kafka.bootstrap-servers`: Connection details for the Kafka broker.


## Installation & Setup
      Create a .env file:
In the root of the project, create a file named .env and add your JWT secret. This file should not be committed to Git.

      JWT_SECRET=MySuperSecretKeyThatIsVeryLongAndSecureAndRandom

### Clone the repository:

      git clone <your-repository-url>
      cd task-manager

### Build and run the entire stack with Docker Compose:
This single command will build the Docker image for your application and start all the necessary infrastructure containers (MongoDB, Redis, Kafka).

      docker-compose up --build -d

The application will be available at http://localhost:8080.


### To stop the services:

      docker-compose down


## Security
This application is secured using Spring Security and JSON Web Tokens (JWT). All endpoints under `/api/tasks` are protected and require a valid JWT Bearer Token in the `Authorization` header.

### Authentication Flow
#### 1. Register a new user: 
Send a `POST` request to `/api/auth/register` with a username and password.

      {
      "username": "user",
      "password": "password123"
      }

#### 2. Log in to get a token:
Send a POST request to `/api/auth/login` with your credentials. The server will respond with a JWT.

      {
      "username": "user",
      "password": "password123"
      }

#### 3. Access protected endpoints:
Include the received token in the Authorization header for all subsequent requests to `/api/tasks`.

      Authorization: Bearer <your-jwt-token-here>

## Running Tests
You can run all the unit and integration tests using the following Maven command:

      mvn test

## API Endpoints
The base URL for the API is `/api/tasks`.

### Public Endpoints
| Method | Endpoint            | Description |
|:-------|:--------------------| :---------- |
| `POST` | `/api/auth/register`  | Register a new user. |
| `POST`   | `/api/auth/login`  |  Log in and receive a JWT. |

### Protected Endpoints (Require Authentication)
| Method | Endpoint            | Description                 |
| :----- |:--------------------|:----------------------------|
| `GET` | `/api/tasks`  | Retrieve all tasks.         |
| `GET` | `/api/tasks/{id}`  | Retrieve a single task by ID. |
| `POST`   | `/`               | Create a new task.          | 
| `PUT`    | `/{id}`           | Update an existing task.    | 
| `DELETE` | `/{id}`           | Delete a task.            |


## API Documentation
This project includes interactive API documentation powered by OpenAPI 3 and Swagger UI.

Once the application is running, you can access the documentation in your browser at:

http://localhost:8080/swagger-ui.html

### From the Swagger UI, you can:

- View all available endpoints.

- See detailed information about request parameters, bodies, and responses.

- Authorize your session by clicking the "Authorize" button and entering your JWT token in the format Bearer <your-token>.

- Execute API requests directly from the documentation to test the endpoints.