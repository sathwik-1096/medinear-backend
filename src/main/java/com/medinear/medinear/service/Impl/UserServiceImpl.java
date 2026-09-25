package com.medinear.medinear.service.Impl;

import com.medinear.medinear.dto.UpdateUserRequestDto;
import com.medinear.medinear.dto.UserResponseDto;
import com.medinear.medinear.entity.Bill;
import com.medinear.medinear.entity.User;
import com.medinear.medinear.enums.Role;
import com.medinear.medinear.repository.BillRepository;
import com.medinear.medinear.repository.UserRepository;
import com.medinear.medinear.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BillRepository billRepository;

    public UserServiceImpl(UserRepository userRepository,
                           BillRepository billRepository) {
        this.userRepository = userRepository;
        this.billRepository = billRepository;
    }

    @Override
    public UserResponseDto updateUser(
            Long id,
            UpdateUserRequestDto request) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        checkUserAccess(existingUser);

        if (!existingUser.getEmail()
                .equals(request.getEmail())
                && userRepository.existsByEmail(
                request.getEmail())) {

            throw new RuntimeException(
                    "Email already exists");
        }

        if (!existingUser.getPhoneNumber()
                .equals(request.getPhoneNumber())
                && userRepository.existsByPhoneNumber(
                request.getPhoneNumber())) {

            throw new RuntimeException(
                    "Phone number already exists");
        }

        existingUser.setFirstName(
                request.getFirstName());

        existingUser.setLastName(
                request.getLastName());

        existingUser.setEmail(
                request.getEmail());

        existingUser.setPhoneNumber(
                request.getPhoneNumber());

        return convertToDto(
                userRepository.save(existingUser));
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        checkUserAccess(user);

        userRepository.delete(user);
    }

    @Override
    public UserResponseDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        checkUserAccess(user);

        return convertToDto(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User loggedInUser = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (loggedInUser.getRole() != Role.ADMIN) {
            throw new RuntimeException(
                    "Only admin can view all users");
        }

        return userRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<Bill> getPreviousOrders(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        checkUserAccess(user);

        return billRepository.findByUser(user);
    }

    private void checkUserAccess(User targetUser) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User loggedInUser = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        boolean isAdmin =
                loggedInUser.getRole() == Role.ADMIN;

        boolean isSameUser =
                loggedInUser.getId()
                        .equals(targetUser.getId());

        if (!isAdmin && !isSameUser) {
            throw new RuntimeException(
                    "You are not allowed to access this user");
        }
    }

    private UserResponseDto convertToDto(User user) {

        UserResponseDto dto =
                new UserResponseDto();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());

        return dto;
    }
}