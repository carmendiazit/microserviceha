# Stage 1: Build (Usamos Maven con Java 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
  
  # Copiamos el pom padre y el código del servicio
COPY pom.xml .
COPY inventory-service ./inventory-service
COPY order-service ./order-service
COPY product-service ./product-service

  # Instalamos solo las dependencias necesarias y compilamos el módulo
RUN mvn clean package -Dmaven.test.skip=true -pl inventory-service -am
  # Stage 2: Run
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/inventory-service/target/*.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]