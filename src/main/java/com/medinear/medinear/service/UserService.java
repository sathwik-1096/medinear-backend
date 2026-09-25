package com.medinear.medinear.service;

import com.medinear.medinear.dto.UpdateUserRequestDto;
import com.medinear.medinear.dto.UserResponseDto;
import com.medinear.medinear.entity.Bill;
import com.medinear.medinear.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponseDto updateUser(
            Long id,
            UpdateUserRequestDto request);

    void deleteUser(Long id);

    UserResponseDto getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByPhoneNumber(String phoneNumber);

    List<UserResponseDto> getAllUsers();

    List<Bill> getPreviousOrders(Long userId);
}