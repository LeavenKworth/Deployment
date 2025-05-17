package com.airline.ticketing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightRequestDTO {
    
    @NotBlank(message = "Flight number is required")
    private String flightNumber;
    
    @NotBlank(message = "Departure airport is required")
    private String airportFrom;
    
    @NotBlank(message = "Arrival airport is required")
    private String airportTo;
    
    @NotNull(message = "Departure date is required")
    private LocalDateTime dateFrom;
    
    @NotNull(message = "Arrival date is required")
    private LocalDateTime dateTo;
    
    @NotNull(message = "Flight duration is required")
    @Positive(message = "Duration must be positive")
    private Integer duration;
    
    @NotNull(message = "Flight capacity is required")
    @Positive(message = "Capacity must be positive")
    private Integer capacity;
} 