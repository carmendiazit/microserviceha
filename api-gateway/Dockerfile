# Etapa 1: Build
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# 1. Copiar el POM raíz
COPY pom.xml .

# 2. Copiar TODAS las carpetas de los módulos (solo los POMs primero para caché)
# Esto evita que Maven se queje de módulos faltantes
COPY discovery-server/pom.xml discovery-server/
COPY api-gateway/pom.xml api-gateway/
COPY inventory-service/pom.xml inventory-service/
COPY order-service/pom.xml order-service/
COPY product-service/pom.xml product-service/

# 3. Descargar dependencias (ahora Maven ve todos los módulos y no falla)
RUN mvn dependency:go-offline -B -pl api-gateway -am

# 4. Copiar el código fuente específico del Gateway
COPY api-gateway/src api-gateway/src
# IMPORTANTE: Si api-gateway depende de clases de otros módulos, deberás copiarlos también
# COPY discovery-server/src discovery-server/src

# 5. Build
RUN mvn clean package -pl api-gateway -am -DskipTests=true


# Etapa 2: Runtime (Esta parte está perfecta)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN apk add --no-cache wget
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/api-gateway/target/api-gateway*.jar app.jar

EXPOSE 8080
ENV JAVA_OPTS="-Xmx512m -Xms256m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]