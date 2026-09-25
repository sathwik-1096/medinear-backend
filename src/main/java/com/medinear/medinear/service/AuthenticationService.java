package com.medinear.medinear.service;

import com.medinear.medinear.dto.LoginRequestDto;
import com.medinear.medinear.dto.LoginResponseDto;
import com.medinear.medinear.dto.RegisterRequestDto;

public interface AuthenticationService {

    LoginResponseDto registerConsumer(RegisterRequestDto request);

    LoginResponseDto registerOwner(RegisterRequestDto request);

    LoginResponseDto loginConsumer(LoginRequestDto request);

    LoginResponseDto loginOwner(LoginRequestDto request);

    LoginResponseDto loginAdmin(LoginRequestDto request);
}
