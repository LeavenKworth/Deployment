package com.airline.ticketing.service;

import com.airline.ticketing.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface TicketService {
    
    ApiResponseDTO<TicketDTO> buyTicket(TicketRequestDTO ticketRequestDTO);
    
    ApiResponseDTO<TicketDTO> checkIn(CheckInRequestDTO checkInRequestDTO);
    
    Page<TicketDTO> getPassengerList(String flightNumber, LocalDateTime date, Pageable pageable);
} 