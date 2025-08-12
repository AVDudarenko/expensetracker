package com.example.expensetracker.controller;

import com.example.expensetracker.common.ApiResponse;
import com.example.expensetracker.dto.CurrentUserDto;
import com.example.expensetracker.dto.UserResponseDto;
import com.example.expensetracker.model.User;
import com.example.expensetracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers() {
        List<UserResponseDto> all = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("user.list", all));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<CurrentUserDto>> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        CurrentUserDto currentUserDto = new CurrentUserDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .toList()
        );

        return ResponseEntity.ok(ApiResponse.success("user.me", currentUserDto));
    }
}
