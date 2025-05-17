package com.airline.ticketing.service.impl;

import com.airline.ticketing.dto.*;
import com.airline.ticketing.model.Flight;
import com.airline.ticketing.model.Ticket;
import com.airline.ticketing.repository.FlightRepository;
import com.airline.ticketing.repository.TicketRepository;
import com.airline.ticketing.service.TicketService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;

    @Override
    @Transactional
    public ApiResponseDTO<TicketDTO> buyTicket(TicketRequestDTO ticketRequestDTO) {
        
        Flight flight = flightRepository.findByFlightNumberAndDate(
                ticketRequestDTO.getFlightNumber(), ticketRequestDTO.getDate())
                .orElseThrow(() -> new EntityNotFoundException("Flight not found"));

        
        if (flight.getAvailableSeats() < ticketRequestDTO.getPassengerNames().size()) {
            return ApiResponseDTO.error("Not enough seats available");
        }

        List<Ticket> tickets = new ArrayList<>();
        List<TicketDTO> ticketDTOs = new ArrayList<>();

        
        for (String passengerName : ticketRequestDTO.getPassengerNames()) {
            Ticket ticket = new Ticket();
            ticket.setTicketNumber(generateTicketNumber());
            ticket.setPassengerName(passengerName);
            ticket.setBookingDate(LocalDateTime.now());
            ticket.setFlight(flight);
            
            tickets.add(ticket);
            ticketDTOs.add(mapToDTO(ticket));
        }

        
        flight.setAvailableSeats(flight.getAvailableSeats() - tickets.size());
        flightRepository.save(flight);
        
        
        ticketRepository.saveAll(tickets);

        
        return ApiResponseDTO.success("Tickets purchased successfully", ticketDTOs.get(0));
    }

    @Override
    @Transactional
    public ApiResponseDTO<TicketDTO> checkIn(CheckInRequestDTO checkInRequestDTO) {
        
        Ticket ticket = ticketRepository.findByFlightNumberDateAndPassengerName(
                checkInRequestDTO.getFlightNumber(),
                checkInRequestDTO.getDate(),
                checkInRequestDTO.getPassengerName())
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        
        if (ticket.getSeatNumber() != null) {
            return ApiResponseDTO.error("Passenger already checked in");
        }

        
        Flight flight = flightRepository.findByFlightNumberAndDate(
                checkInRequestDTO.getFlightNumber(), checkInRequestDTO.getDate())
                .orElseThrow(() -> new EntityNotFoundException("Flight not found"));

        
        List<Ticket> checkedInTickets = ticketRepository.findByFlightNumberAndDate(
                checkInRequestDTO.getFlightNumber(), checkInRequestDTO.getDate()).stream()
                .filter(t -> t.getSeatNumber() != null)
                .toList();

        
        int nextSeat = 1;
        List<Integer> occupiedSeats = checkedInTickets.stream()
                .map(Ticket::getSeatNumber)
                .collect(Collectors.toList());

        while (occupiedSeats.contains(nextSeat)) {
            nextSeat++;
        }

        ticket.setSeatNumber(nextSeat);

        
        Ticket savedTicket = ticketRepository.save(ticket);

        
        return ApiResponseDTO.success("Check-in successful", mapToDTO(savedTicket));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketDTO> getPassengerList(String flightNumber, LocalDateTime date, Pageable pageable) {
        
        Page<Ticket> tickets = ticketRepository.findByFlightNumberAndDatePaginated(flightNumber, date, pageable);

        
        return tickets.map(this::mapToDTO);
    }

    private String generateTicketNumber() {
        return "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private TicketDTO mapToDTO(Ticket ticket) {
        return new TicketDTO(
                ticket.getTicketNumber(),
                ticket.getFlight().getFlightNumber(),
                ticket.getPassengerName(),
                ticket.getBookingDate(),
                ticket.getSeatNumber()
        );
    }
} 