package com.airline.ticketing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    
    private String flightNumber;
    private String airportFrom;
    private String airportTo;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Integer duration;
    private Integer capacity;
    private Integer availableSeats;
} 