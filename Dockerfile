# 1. Build aşaması
FROM maven:3.8.7-openjdk-17 AS build

WORKDIR /app

# Önce pom.xml'i kopyala ve bağımlılıkları indir (cache için)
COPY pom.xml .

RUN mvn dependency:go-offline

# Kaynak kodları kopyala
COPY src ./src

# Projeyi package et (testleri atlamak için -DskipTests kullanabilirsin)
RUN mvn clean package -DskipTests

# 2. Çalışma aşaması
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Build aşamasından jar dosyasını kopyala
COPY --from=build /app/target/airline-ticketing-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
