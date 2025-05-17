package com.airline.ticketing.controller;

import com.airline.ticketing.dto.*;
import com.airline.ticketing.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/{version}/tickets")
@RequiredArgsConstructor
@Tag(name = "Tickets", description = "Ticket management API")
public class TicketController {

    private final TicketService ticketService;

    @Operation(
        summary = "Buy a ticket", 
        description = "Purchases tickets for the specified flight for one or more passengers. Requires authentication.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tickets purchased successfully",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    @PostMapping("/buy")
    public ResponseEntity<ApiResponseDTO<TicketDTO>> buyTicket(
            @Parameter(description = "API version", example = "v1") @PathVariable String version,
            @Valid @RequestBody TicketRequestDTO ticketRequestDTO) {
        ApiResponseDTO<TicketDTO> response = ticketService.buyTicket(ticketRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Check-in for a flight", 
        description = "Performs check-in for a passenger and assigns a seat number"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Check-in successful",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Ticket or flight not found")
    })
    @PostMapping("/check-in")
    public ResponseEntity<ApiResponseDTO<TicketDTO>> checkIn(
            @Parameter(description = "API version", example = "v1") @PathVariable String version,
            @Valid @RequestBody CheckInRequestDTO checkInRequestDTO) {
        ApiResponseDTO<TicketDTO> response = ticketService.checkIn(checkInRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Get passenger list for a flight", 
        description = "Retrieves the list of passengers for a specific flight with pagination. Requires ADMIN role.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden, requires ADMIN role"),
        @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    @GetMapping("/passenger-list")
    public ResponseEntity<Page<TicketDTO>> getPassengerList(
            @Parameter(description = "API version", example = "v1") @PathVariable String version,
            @Parameter(description = "Flight number", required = true) @RequestParam String flightNumber,
            @Parameter(description = "Flight date", required = true) 
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @Parameter(description = "Page number (zero-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<TicketDTO> passengers = ticketService.getPassengerList(flightNumber, date, pageRequest);
        return ResponseEntity.ok(passengers);
    }
} 