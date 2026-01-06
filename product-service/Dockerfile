FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

  # Copiamos el POM padre y todos los módulos para que Maven pueda validar la estructura
COPY pom.xml .
COPY discovery-server/pom.xml discovery-server/
COPY api-gateway/pom.xml api-gateway/
COPY inventory-service ./inventory-service
COPY order-service ./order-service
COPY product-service ./product-service

  # Construimos solo este módulo y sus dependencias necesarias
RUN mvn clean package -Dmaven.test.skip=true -pl product-service -am

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/product-service/target/*.jar app.jar

EXPOSE 0
ENTRYPOINT ["java", "-jar", "app.jar"]