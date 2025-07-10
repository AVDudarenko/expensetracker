package com.example.expensetracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @Email(message = "wrong format of email")
    @NotBlank(message = "Email is require")
    private String email;

    @Size(min = 6, message = "Password must contains minimum 6 characters")
    @NotBlank(message = "Password is require")
    private String password;

    @NotBlank(message = "Name is require")
    private String name;

    @NotBlank(message = "Surname is require")
    private String surname;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
