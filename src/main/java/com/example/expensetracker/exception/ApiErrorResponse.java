package com.example.expensetracker.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ApiErrorResponse {
    private List<String> errors;
    private LocalDateTime timeStamp;

    public ApiErrorResponse(List<String> errors) {
        this.errors = errors;
        this.timeStamp = LocalDateTime.now();
    }

    public List<String> getErrors() {
        return errors;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
