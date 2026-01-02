# Microservices Architecture with Spring Boot & Docker

This repository contains a decoupled microservices architecture built with **Java 21** and **Spring Boot 3.x**. The project is currently in its baseline stage, focusing on containerization and core service communication.

## 🚀 Current Status: Baseline Stable (v1.0)
This version marks the completion of the containerized core services before the integration of Spring Cloud components.

### Tech Stack
* **Java 21**: Leveraging Virtual Threads and modern language features.
* **Spring Boot 3.2+**: Core framework for microservices.
* **Spring WebFlux**: Reactive web stack for non-blocking services.
* **Docker & Docker Compose**: Orchestrating multi-container deployments.
* **MySQL**: Per-service isolated databases to ensure data sovereignty.
* **PostgreSQL**: Robust relational database for persistent storage.

## 🏗️ Architecture
The system is divided into independent services:
* **Order Service**: Manages customer orders and business logic.
* **Inventory Service**: Handles stock levels and product availability.
* **Product Service**: Catalog management.

## 🛠️ How to Run
To spin up the entire ecosystem, ensure you have Docker installed and run:

```bash
docker-compose up --build