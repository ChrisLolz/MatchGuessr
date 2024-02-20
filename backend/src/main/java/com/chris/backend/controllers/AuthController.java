package com.chris.backend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chris.backend.models.Account;
import com.chris.backend.payload.request.LoginRequest;
import com.chris.backend.payload.request.SignupRequest;
import com.chris.backend.payload.response.JwtResponse;
import com.chris.backend.payload.response.MessageResponse;
import com.chris.backend.security.jwt.JwtUtils;
import com.chris.backend.security.services.AccDetailsImpl;
import com.chris.backend.services.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
  
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        AccDetailsImpl userDetails = (AccDetailsImpl) authentication.getPrincipal();    
            
        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, 
                                userDetails.getId(), 
                                userDetails.getUsername(), 
                                roles));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken() {
        AccDetailsImpl userDetails = (AccDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails != null) {
            return ResponseEntity.ok(new MessageResponse("Token is valid!"));
        }
        return ResponseEntity
            .badRequest()
            .body(new MessageResponse("Error: Invalid token!"));
    }
  
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (accountService.findByUsername(signUpRequest.getUsername()) != null) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Username is already taken!"));
        }
  
        Account account = new Account(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));

        accountService.save(account);
  
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword()));
    
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        AccDetailsImpl userDetails = (AccDetailsImpl) authentication.getPrincipal();    
            
        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, 
                                userDetails.getId(), 
                                userDetails.getUsername(), 
                                roles));
    }
}