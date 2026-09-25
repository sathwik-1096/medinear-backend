package com.medinear.medinear.controller;

import com.medinear.medinear.dto.LoginRequestDto;
import com.medinear.medinear.dto.LoginResponseDto;
import com.medinear.medinear.dto.RegisterRequestDto;
import com.medinear.medinear.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/user/register")
    public ResponseEntity<LoginResponseDto> registerUser(
            @Valid @RequestBody RegisterRequestDto request) {

        return ResponseEntity.ok(authenticationService.registerConsumer(request));
    }

    @PostMapping("/owner/register")
    public ResponseEntity<LoginResponseDto> registerOwner(
            @Valid @RequestBody RegisterRequestDto request) {

        return ResponseEntity.ok(authenticationService.registerOwner(request));
    }

    @PostMapping("/user/login")
    public ResponseEntity<LoginResponseDto> loginUser(
            @Valid @RequestBody LoginRequestDto request) {

        return ResponseEntity.ok(authenticationService.loginConsumer(request));
    }

    @PostMapping("/owner/login")
    public ResponseEntity<LoginResponseDto> loginOwner(
            @Valid @RequestBody LoginRequestDto request) {

        return ResponseEntity.ok(authenticationService.loginOwner(request));
    }

    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponseDto> loginAdmin(
            @Valid @RequestBody LoginRequestDto request) {

        return ResponseEntity.ok(authenticationService.loginAdmin(request));
    }
}