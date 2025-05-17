package com.airline.ticketing.service.impl;

import com.airline.ticketing.dto.ApiResponseDTO;
import com.airline.ticketing.dto.FlightDTO;
import com.airline.ticketing.dto.FlightQueryDTO;
import com.airline.ticketing.dto.FlightRequestDTO;
import com.airline.ticketing.model.Flight;
import com.airline.ticketing.repository.FlightRepository;
import com.airline.ticketing.service.FlightService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    @Transactional
    public ApiResponseDTO<FlightDTO> addFlight(FlightRequestDTO flightRequestDTO) {
        
        Optional<Flight> existingFlight = flightRepository.findByFlightNumber(flightRequestDTO.getFlightNumber());
        if (existingFlight.isPresent()) {
            return ApiResponseDTO.error("Flight with this number already exists");
        }

        Flight flight = new Flight();
        flight.setFlightNumber(flightRequestDTO.getFlightNumber());
        flight.setAirportFrom(flightRequestDTO.getAirportFrom());
        flight.setAirportTo(flightRequestDTO.getAirportTo());
        flight.setDateFrom(flightRequestDTO.getDateFrom());
        flight.setDateTo(flightRequestDTO.getDateTo());
        flight.setDuration(flightRequestDTO.getDuration());
        flight.setCapacity(flightRequestDTO.getCapacity());
        flight.setAvailableSeats(flightRequestDTO.getCapacity()); 

        Flight savedFlight = flightRepository.save(flight);
        
        FlightDTO flightDTO = mapToDTO(savedFlight);
        return ApiResponseDTO.success("Flight added successfully", flightDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FlightDTO> queryFlights(FlightQueryDTO flightQueryDTO, Pageable pageable) {
        
        Page<Flight> flights = flightRepository.findAvailableFlights(
                flightQueryDTO.getDateFrom(),
                flightQueryDTO.getDateTo(),
                flightQueryDTO.getAirportFrom(),
                flightQueryDTO.getAirportTo(),
                pageable
        );

        return flights.map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public FlightDTO getFlightByFlightNumberAndDate(String flightNumber, LocalDateTime date) {
        Flight flight = flightRepository.findByFlightNumberAndDate(flightNumber, date)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found"));
        return mapToDTO(flight);
    }

    private FlightDTO mapToDTO(Flight flight) {
        return new FlightDTO(
                flight.getFlightNumber(),
                flight.getAirportFrom(),
                flight.getAirportTo(),
                flight.getDateFrom(),
                flight.getDateTo(),
                flight.getDuration(),
                flight.getCapacity(),
                flight.getAvailableSeats()
        );
    }
} 