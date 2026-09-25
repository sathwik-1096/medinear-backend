package com.medinear.medinear.controller;

import com.medinear.medinear.dto.UpdateUserRequestDto;
import com.medinear.medinear.dto.UserResponseDto;
import com.medinear.medinear.entity.Bill;
import com.medinear.medinear.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get user by ID
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(
            @PathVariable String email) {

        return userService.getUserByEmail(email)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    // Get user by phone number
    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<UserResponseDto> getUserByPhoneNumber(
            @PathVariable String phoneNumber) {

        return userService.getUserByPhoneNumber(phoneNumber)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    // Get all users
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    // Update user
    @PutMapping("/{id}")
    public UserResponseDto updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequestDto request) {

        return userService.updateUser(id, request);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(
                "User deleted successfully");
    }

    // Get previous orders of a user
    @GetMapping("/{id}/orders")
    public List<Bill> getPreviousOrders(
            @PathVariable Long id) {

        return userService.getPreviousOrders(id);
    }

    private UserResponseDto convertToDto(
            com.medinear.medinear.entity.User user) {

        UserResponseDto dto = new UserResponseDto();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());

        return dto;
    }
}