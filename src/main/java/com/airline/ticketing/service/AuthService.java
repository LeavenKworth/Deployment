package com.airline.ticketing.service;

import com.airline.ticketing.dto.AuthRequestDTO;
import com.airline.ticketing.dto.AuthResponseDTO;

public interface AuthService {
    
    AuthResponseDTO login(AuthRequestDTO authRequestDTO);
    
    String generateToken(String username);
    
    boolean validateToken(String token);
} 