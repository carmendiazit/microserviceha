🛒 Microservices E-Commerce System
A robust, production-ready microservices architecture built with Spring Boot 3.2+ and Java 21 (LTS). This project implements modern distributed systems patterns such as API Gateway, Service Discovery, and advanced Resilience patterns.

### Tech Stack
* **Java 21**: Leveraging Virtual Threads and modern language features.
* **Spring Boot 3.2+**: Core framework for microservices.
* **Spring WebFlux**: Reactive web stack for non-blocking services.
* **Docker & Docker Compose**: Orchestrating multi-container deployments.
* **MySQL**: Per-service isolated databases to ensure data sovereignty.
* **PostgreSQL**: Robust relational database for persistent storage.

🛠️ Technology StackComponentTechnologyBackendJava 21 (LTS), Spring Boot 3.2.xConcurrencyVirtual Threads (Project Loom) enabledService DiscoverySpring Cloud Netflix EurekaAPI GatewaySpring Cloud Gateway (Reactive / WebFlux)ResilienceResilience4j (Circuit Breaker, Time Limiter, Retry)SecurityKeycloak (OAuth2 / OIDC), Spring SecurityDatabasePostgreSQL / MySQL (Dockerized)ContainerizationDocker & Docker Compose
## 🏗️ Architecture
The system is divided into independent services:
* **Order Service**: Manages customer orders and business logic.
* **Inventory Service**: Handles stock levels and product availability.
* **Product Service**: Catalog management.
* **Concurrency	Virtual Threads**: (Project Loom) enabled
* **Service Discovery**:	Spring Cloud Netflix Eureka
* **API Gateway**: Spring Cloud Gateway (Reactive / WebFlux)
* **Resilience**: Resilience4j (Circuit Breaker, Time Limiter, Retry)
* **Security**: Keycloak (OAuth2 / OIDC), Spring Security

## 🏗️ System Architecture
The ecosystem consists of the following core modules:

* **Discovery Server**: Centralized registry for service instances.

* **API Gateway**: Secure entry point with routing and path rewriting.

* **Order Service**: Handles purchases, integrating with Inventory via WebClient.

* **Inventory Service**: Real-time stock management.

* **Product Service**: Product catalog management.

## 🚀 Local Deployment Guide
* **1.Prerequisites**
* **Docker & Docker Compose** installed.

* **Java 21** (JDK) installed.

* **Maven** 3.9+ (or use the provided wrapper).

* **2. Build the Project**
From the root directory, compile and package all modules into executable JARs:

```bash
mvn clean package -DskipTests
```

## 🛠️ How to Run
* **3. Launch with Docker Compose**
   Run the entire infrastructure and microservices in detached mode:

```bash
docker-compose up -d --build
```
Note: The first run might take a few minutes as it pulls images for Keycloak and Databases.

## 4. Keycloak Initial Setup
1. * **Access Console:** Go to http://localhost:8181 (assuming Keycloak is mapped to a different port to avoid conflict with the Gateway at 8080).

2. * **Create Realm:** Click on the master realm dropdown and select Create Realm. Name it microserviceha-realm.

3. * **Create Client:** Client ID: microserviceha_client.
   * **Client Protocol:** openid-connect.
   * **Capability Config:** Ensure Client Authentication is ON (for confidential access) and Authorization is ON.
   * **Valid Redirect URIs:** Set to http://localhost:8080/login/oauth2/code/* (this allows the Gateway to handle the callback).

4. * **Configure Roles:** Go to Realm Roles and create ROLE_USER and ROLE_ADMIN.

5. * **Create User:** Create a test user, set a password in the Credentials tab (turn off "Temporary"), and assign the roles in Role Mapping.

* Note: You can set part of this configuration on * **api-gateway/application.properties** and Keycloak console. 

## 🛡️ Resilience & Chaos Testing
This project uses * **Resilience4j** to prevent cascading failures. To test the Circuit Breaker:

1. * **Monitor Health:** Open http://localhost:8080/order-service/actuator/health in your browser.

2. * **Inject Failure:** Use the provided chaos script to simulate an unstable Inventory service:

```bash
chmod +x chaos-script.sh
./chaos-script.sh
```
3. * **Observe:** Watch the Circuit Breaker state transition from CLOSED to OPEN and then HALF_OPEN automatically.