package com.airline.ticketing.service;

import com.airline.ticketing.dto.ApiResponseDTO;
import com.airline.ticketing.dto.FlightDTO;
import com.airline.ticketing.dto.FlightQueryDTO;
import com.airline.ticketing.dto.FlightRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface FlightService {
    
    ApiResponseDTO<FlightDTO> addFlight(FlightRequestDTO flightRequestDTO);
    
    Page<FlightDTO> queryFlights(FlightQueryDTO flightQueryDTO, Pageable pageable);
    
    FlightDTO getFlightByFlightNumberAndDate(String flightNumber, LocalDateTime date);
} 