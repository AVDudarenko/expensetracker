package com.example.expensetracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "wrong format of email")
    @NotBlank(message = "Email is require")
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 6, message = "Password must contains minimum 6 characters")
    @NotBlank(message = "Password is require")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Name is require")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Surname is require")
    @Column(nullable = false)
    private String surname;

    public Long getId() {
        return id;
    }

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
