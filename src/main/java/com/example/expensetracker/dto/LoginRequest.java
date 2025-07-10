package com.example.expensetracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @Email(message = "Wrong format of email")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 6, message = "Password must contains minimum 6 characters")
    @NotBlank(message = "Password is require")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
