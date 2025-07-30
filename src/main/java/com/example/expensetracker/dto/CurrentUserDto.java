package com.example.expensetracker.dto;

import java.util.List;

public class CurrentUserDto {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private List<String> roles;

    public CurrentUserDto() {

    }

    public CurrentUserDto(
            Long id, String email, String name, String surname, List<String> roles
    ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
