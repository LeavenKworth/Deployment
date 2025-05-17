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
public class FlightQueryDTO {
    
    @NotNull(message = "From date is required")
    private LocalDateTime dateFrom;
    
    @NotNull(message = "To date is required")
    private LocalDateTime dateTo;
    
    @NotBlank(message = "Departure airport is required")
    private String airportFrom;
    
    @NotBlank(message = "Arrival airport is required")
    private String airportTo;
    
    @NotNull(message = "Number of passengers is required")
    @Positive(message = "Number of passengers must be positive")
    private Integer numberOfPeople;
    
    private Boolean oneWay = true; 
} 