package com.airline.ticketing.repository;

import com.airline.ticketing.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightNumber(String flightNumber);

    @Query("select f FROM Flight f WHERE f.dateFrom >= :dateFrom AND f.dateTo <= :dateTo " +
            "AND f.airportFrom = :airportFrom AND f.airportTo = :airportTo " +
            "AND f.availableSeats > 0")
    Page<Flight> findAvailableFlights(
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo,
            @Param("airportFrom") String airportFrom,
            @Param("airportTo") String airportTo,
            Pageable pageable);
            
    @Query("select f FROM Flight f WHERE f.flightNumber = :flightNumber AND f.dateFrom = :date")
    Optional<Flight> findByFlightNumberAndDate(
            @Param("flightNumber") String flightNumber,
            @Param("date") LocalDateTime date);
} 