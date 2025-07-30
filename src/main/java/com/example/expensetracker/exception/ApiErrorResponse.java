package com.example.expensetracker.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.List;

public class ApiErrorResponse {
    private List<String> errors;
    private LocalDateTime timestamp;
    private int status;

    @JsonPropertyOrder({"timestamp", "status", "errors"})
    public ApiErrorResponse(List<String> errors, int status) {
        this.status = status;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    public List<String> getErrors() {
        return errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }
}
