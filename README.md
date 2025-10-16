# Real-time Chat Application

Spring Boot chat application with WebSocket and REST API.

## Features
- Real-time messaging via WebSocket
- REST API for user management
- Swagger/OpenAPI documentation
- In-memory storage

## Tech Stack
- Java 21
- Spring Boot 3.2
- WebSocket (STOMP)
- Maven
- SpringDoc OpenAPI

## API Documentation
After starting the application:
- Swagger UI: http://localhost:8080/swagger-ui.html
- REST API: http://localhost:8080/api/**

## WebSocket
- Connect: ws://localhost:8080/ws
- Send: /app/chat.send
- Subscribe: /topic/messages

## Quick Start
```bash
cd backend
mvn spring-boot:run
