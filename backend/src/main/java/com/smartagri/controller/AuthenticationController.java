package com.smartagri.controller;

import com.smartagri.dto.AuthenticationRequest;
import com.smartagri.dto.AuthenticationResponse;
import com.smartagri.dto.RegisterRequest;
import com.smartagri.service.AuthenticationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private static final Logger log =
            LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService service;

   
    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            AuthenticationResponse response = service.register(request);
            return ResponseEntity.ok(response);

        } catch (ResponseStatusException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getReason());
            return ResponseEntity.status(e.getStatusCode()).body(error);

        } catch (Exception e) {
            if (e.getCause() != null) {
                log.error("Caused by: {}", e.getCause().getMessage());
            }

           

            Map<String, String> error = new HashMap<>();
            error.put("error", "Registration failed: " + e.getMessage());
            error.put("type", e.getClass().getSimpleName());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(error);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
           

            AuthenticationResponse response = service.authenticate(request);

            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
         

            Map<String, String> error = new HashMap<>();
            error.put("error", "Authentication failed: " + e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(error);
        }
    }
}
