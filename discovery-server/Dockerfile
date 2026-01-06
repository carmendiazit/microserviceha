FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .

COPY discovery-server ./discovery-server
COPY api-gateway ./api-gateway
COPY inventory-service ./inventory-service
COPY order-service ./order-service
COPY product-service ./product-service

RUN mvn clean package -Dmaven.test.skip=true -pl discovery-server -am

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/discovery-server/target/*.jar app.jar

EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]