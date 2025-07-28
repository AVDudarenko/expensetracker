package com.example.expensetracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AdminExpenseResponseDto {

    private Long id;
    private String title;
    private BigDecimal amount;
    private String category;
    private LocalDate date;
    private Long userId;
    private String userEmail;

    public AdminExpenseResponseDto() {
    }

    public AdminExpenseResponseDto(Long id, String title, BigDecimal amount, String category,
                                   LocalDate date, Long userId) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
