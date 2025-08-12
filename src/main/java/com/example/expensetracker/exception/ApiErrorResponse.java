package com.example.expensetracker.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.List;

@JsonPropertyOrder({"timestamp", "code", "message", "httpStatus", "errors"})
public class ApiErrorResponse {
    private final String status = "error";
    private final LocalDateTime timeStamp = LocalDateTime.now();

    private String code;
    private String message;
    private int httpStatus;
    private List<String> errors;

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(String code, String message, int httpStatus, List<String> errors) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
