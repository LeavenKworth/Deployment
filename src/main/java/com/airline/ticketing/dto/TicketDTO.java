package com.airline.ticketing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    
    private String ticketNumber;
    private String flightNumber;
    private String passengerName;
    private LocalDateTime bookingDate;
    private Integer seatNumber;
} 