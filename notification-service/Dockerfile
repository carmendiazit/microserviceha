# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos el POM padre y la estructura de módulos para resolver dependencias
COPY pom.xml .
COPY discovery-server/pom.xml ./discovery-server/
COPY api-gateway/pom.xml ./api-gateway/
COPY inventory-service/pom.xml ./inventory-service/
COPY order-service/pom.xml ./order-service/
COPY product-service/pom.xml ./product-service/
COPY notification-service/pom.xml ./notification-service/


COPY notification-service/src ./notification-service/src

# Construimos específicamente el módulo de notificaciones y sus dependencias
RUN mvn clean package -Dmaven.test.skip=true -pl notification-service -am

# Stage 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos el artefacto construido en el Stage 1
COPY --from=build /app/notification-service/target/*.jar app.jar

# Exponemos puerto 0 para que Spring elija uno dinámico o el configurado en properties
EXPOSE 0

ENTRYPOINT ["java", "-jar", "app.jar"]