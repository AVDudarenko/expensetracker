package com.example.expensetracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseResponseDto {

    private Long id;
    private String title;
    private BigDecimal amount;
    private LocalDate date;
    private String category;

    public ExpenseResponseDto(Long id, String title, BigDecimal amount, LocalDate date, String category) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
