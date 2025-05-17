-- Create database if not exists
CREATE DATABASE IF NOT EXISTS airline_ticketing;

USE airline_ticketing;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Create flights table
CREATE TABLE IF NOT EXISTS flights (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    flight_number VARCHAR(20) NOT NULL UNIQUE,
    airport_from VARCHAR(100) NOT NULL,
    airport_to VARCHAR(100) NOT NULL,
    date_from TIMESTAMP NOT NULL,
    date_to TIMESTAMP NOT NULL,
    duration INT NOT NULL,
    capacity INT NOT NULL,
    available_seats INT NOT NULL
);

-- Create tickets table
CREATE TABLE IF NOT EXISTS tickets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ticket_number VARCHAR(20) NOT NULL UNIQUE,
    passenger_name VARCHAR(100) NOT NULL,
    booking_date TIMESTAMP NOT NULL,
    seat_number INT,
    flight_id BIGINT NOT NULL,
    FOREIGN KEY (flight_id) REFERENCES flights(id)
);

-- Create indexes
CREATE INDEX idx_flight_number_date ON flights(flight_number, date_from);
CREATE INDEX idx_ticket_flight ON tickets(flight_id); 