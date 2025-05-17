# Build aşaması - Maven ve JDK 17 kullanıyoruz
FROM maven:3.9.0-eclipse-temurin-17 AS build

WORKDIR /app

# Bağımlılıkları önceden indiriyoruz (cache için)
COPY pom.xml .
RUN mvn dependency:go-offline

# Kaynak kodları kopyala
COPY src ./src

# Projeyi paketle (testleri atla)
RUN mvn clean package -DskipTests

# Çalışma aşaması - sadece JRE ile
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Build aşamasından jar dosyasını kopyala
COPY --from=build /app/target/airline-ticketing-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
