# Airline Ticketing System API

This is a simple airline ticketing system API.

## Setup

1. Download the project to your computer
2. Set up MySQL database
3. Run the following command:

```
./mvnw spring-boot:run
```

## Using Swagger UI

After starting the application, access Swagger UI in your browser at:

```
http://localhost:8080/swagger-ui.html
```

## API Features

- User login
- Adding and querying flights
- Purchasing tickets
- Check-in
- Viewing passenger lists

## Postman Collection

You can import the Postman collection file (`Airline_Ticketing_API.postman_collection.json`) to test API endpoints.

## Test Users

- Admin: username: admin, password: admin123
- User: username: user, password: user123

## Example Requests

### Login
```json
{
  "username": "admin",
  "password": "admin123"
}
```

### Add Flight
```json
{
  "flightNumber": "TK101",
  "airportFrom": "IST",
  "airportTo": "JFK",
  "dateFrom": "2025-05-15T10:00:00",
  "dateTo": "2025-05-15T22:00:00",
  "duration": 720,
  "capacity": 200
}
```

### Buy Ticket
```json
{
  "flightNumber": "TK101",
  "date": "2025-05-15T10:00:00",
  "passengerNames": ["John Doe", "Jane Doe"]
}
```

## Deployment

### Local Deployment
1. Build the JAR file:
   ```
   ./mvnw clean package
   ```
2. Run the JAR file:
   ```
   java -jar target/airline-ticketing-0.0.1-SNAPSHOT.jar
   ```

### Docker Deployment
1. Build Docker image:
   ```
   docker build -t airline-ticketing-api .
   ```
2. Run Docker container:
   ```
   docker run -p 8080:8080 airline-ticketing-api
   ```

### Cloud Deployment (AWS)
1. Create an EC2 instance
2. Install Java and MySQL
3. Upload the JAR file
4. Configure the application.properties with your database settings
5. Run the application using:
   ```
   java -jar airline-ticketing-0.0.1-SNAPSHOT.jar
   ``` 