package com.airline.ticketing.repository;

import com.airline.ticketing.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByTicketNumber(String ticketNumber);

    @Query("select t FROM Ticket t WHERE t.flight.flightNumber = :flightNumber AND t.flight.dateFrom = :date")
    List<Ticket> findByFlightNumberAndDate(
            @Param("flightNumber") String flightNumber,
            @Param("date") LocalDateTime date);
            
    @Query("select t FROM Ticket t WHERE t.flight.flightNumber = :flightNumber AND t.flight.dateFrom = :date")
    Page<Ticket> findByFlightNumberAndDatePaginated(
            @Param("flightNumber") String flightNumber,
            @Param("date") LocalDateTime date,
            Pageable pageable);
            
    @Query("select t FROM Ticket t WHERE t.flight.flightNumber = :flightNumber AND t.flight.dateFrom = :date AND t.passengerName = :passengerName")
    Optional<Ticket> findByFlightNumberDateAndPassengerName(
            @Param("flightNumber") String flightNumber,
            @Param("date") LocalDateTime date,
            @Param("passengerName") String passengerName);
} 