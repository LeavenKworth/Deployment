# Build stage
FROM maven:3.8.7-jdk-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/airline-ticketing-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
