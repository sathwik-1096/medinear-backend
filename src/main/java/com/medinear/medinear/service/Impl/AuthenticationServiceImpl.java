package com.medinear.medinear.service.Impl;

import com.medinear.medinear.dto.LoginRequestDto;
import com.medinear.medinear.dto.LoginResponseDto;
import com.medinear.medinear.dto.RegisterRequestDto;
import com.medinear.medinear.entity.User;
import com.medinear.medinear.enums.Role;
import com.medinear.medinear.exception.BadRequestException;
import com.medinear.medinear.repository.UserRepository;
import com.medinear.medinear.security.JwtService;
import com.medinear.medinear.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public LoginResponseDto registerConsumer(RegisterRequestDto request) {
        return register(request, Role.CONSUMER);
    }

    @Override
    public LoginResponseDto registerOwner(RegisterRequestDto request) {
        return register(request, Role.OWNER);
    }


    @Override
    public LoginResponseDto loginConsumer(LoginRequestDto request) {
        return login(request, Role.CONSUMER);
    }

    @Override
    public LoginResponseDto loginOwner(LoginRequestDto request) {
        return login(request, Role.OWNER);
    }

    @Override
    public LoginResponseDto loginAdmin(LoginRequestDto request) {
        return login(request, Role.ADMIN);
    }

    private LoginResponseDto register(
            RegisterRequestDto request,
            Role role) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new BadRequestException("Phone number already exists");
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        // Encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(role);

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponseDto(
                token,
                user.getEmail(),
                user.getRole().name()
        );
    }
    private LoginResponseDto login(
            LoginRequestDto request,
            Role expectedRole) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BadRequestException("Invalid email or password"));

        if (user.getRole() != expectedRole) {
            throw new BadRequestException(
                    "Please login through the correct portal."
            );
        }
        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponseDto(
                token,
                user.getEmail(),
                user.getRole().name()
        );
    }
}