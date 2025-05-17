package com.airline.ticketing.controller;

import com.airline.ticketing.dto.ApiResponseDTO;
import com.airline.ticketing.dto.FlightDTO;
import com.airline.ticketing.dto.FlightQueryDTO;
import com.airline.ticketing.dto.FlightRequestDTO;
import com.airline.ticketing.service.FlightService;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{version}/flights")
@RequiredArgsConstructor
@Tag(name = "Flights", description = "Flight management API")
public class FlightController {

    private final FlightService flightService;

    @Operation(
        summary = "Add a new flight", 
        description = "Creates a new flight with the provided details. Requires ADMIN role.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flight added successfully",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "403", description = "Forbidden, requires ADMIN role")
    })
    @PostMapping("/add")
    public ResponseEntity<ApiResponseDTO<FlightDTO>> addFlight(
            @Parameter(description = "API version", example = "v1") @PathVariable String version,
            @Valid @RequestBody FlightRequestDTO flightRequestDTO) {
        ApiResponseDTO<FlightDTO> response = flightService.addFlight(flightRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Query available flights", 
        description = "Searches for available flights based on the provided criteria with pagination"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/query")
    public ResponseEntity<Page<FlightDTO>> queryFlights(
            @Parameter(description = "API version", example = "v1") @PathVariable String version,
            @Valid @RequestBody FlightQueryDTO flightQueryDTO,
            @Parameter(description = "Page number (zero-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("dateFrom"));
        Page<FlightDTO> flights = flightService.queryFlights(flightQueryDTO, pageRequest);
        return ResponseEntity.ok(flights);
    }
} 