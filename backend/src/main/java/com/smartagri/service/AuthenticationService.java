package com.smartagri.service;

import com.smartagri.dto.AuthenticationRequest;
import com.smartagri.dto.AuthenticationResponse;
import com.smartagri.dto.RegisterRequest;
import com.smartagri.model.Role;
import com.smartagri.model.User;
import com.smartagri.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
	    @Autowired
        private UserRepository repository;
	    @Autowired
        private PasswordEncoder passwordEncoder;
	    @Autowired
        private JwtService jwtService;
	    @Autowired
        private AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) {
               
             
                // Check if email already exists
                if (repository.findByEmail(request.getEmail()).isPresent()) {
                       
                        throw new org.springframework.web.server.ResponseStatusException(
                                        org.springframework.http.HttpStatus.CONFLICT, "Email already in use");
                }
             

                var role = request.getRole() == null ? Role.FARMER : request.getRole();
            

                var user = new User(
                                null,
                                request.getFullName(),
                                request.getEmail(),
                                passwordEncoder.encode(request.getPassword()),
                                role);
              

                try {
                        User savedUser = repository.save(user);
                     

                    
                        var jwtToken = jwtService.generateToken(user);
                      

                        return new AuthenticationResponse(
                                        jwtToken,
                                        savedUser.getRole(),
                                        savedUser.getId(),
                                        savedUser.getFullName());
                } catch (Exception e) {
                     
                        throw e;
                }
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));
                var user = repository.findByEmail(request.getEmail())
                                .orElseThrow();
                var jwtToken = jwtService.generateToken(user);
                return new AuthenticationResponse(
                                jwtToken,
                                user.getRole(),
                                user.getId(),
                                user.getFullName());
        }
}
