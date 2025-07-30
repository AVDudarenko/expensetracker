package com.example.expensetracker.controller;

import com.example.expensetracker.dto.CurrentUserDto;
import com.example.expensetracker.dto.UserResponseDto;
import com.example.expensetracker.model.User;
import com.example.expensetracker.service.UserService;
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
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/me")
    public CurrentUserDto getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        return new CurrentUserDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .toList()
        );
    }
}
