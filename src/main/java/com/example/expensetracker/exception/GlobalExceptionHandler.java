package com.example.expensetracker.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        ApiErrorResponse body = new ApiErrorResponse(
                "ERR_VALIDATION",
                "Validation failed",
                HttpStatus.BAD_REQUEST.value(),
                errors
        );

        return ResponseEntity.badRequest().body(body);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolations(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        ApiErrorResponse body = new ApiErrorResponse(
                "ERR_VALIDATION",
                "Validation failed",
                HttpStatus.BAD_REQUEST.value(),
                errors
        );

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.warn("Data integrity violation", ex);
        ApiErrorResponse body = new ApiErrorResponse(
                "ERROR_CONFLICT",
                "Data integrity violation",
                HttpStatus.CONFLICT.value(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(body);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthError(AuthenticationException ex) {
        ApiErrorResponse body = new ApiErrorResponse(
                "ERROR_AUTH",
                ex.getMessage() != null ? ex.getMessage() : "Authentication error",
                HttpStatus.FORBIDDEN.value(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(NotFoundException ex) {
        ApiErrorResponse body = new ApiErrorResponse(
                "ERROR_NOT_FOUND",
                ex.getMessage() != null ? ex.getMessage() : "Resource not found",
                HttpStatus.NOT_FOUND.value(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(body);
    }

    @ExceptionHandler(Exception.class) // fallback
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex) {
        log.error("Unexpected error", ex);
        ApiErrorResponse body = new ApiErrorResponse(
                "ERROR_INTERNAL",
                "Unexpected error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDenied(AccessDeniedException exception) {
        ApiErrorResponse body = new ApiErrorResponse(
                "ERROR_FORBIDDEN",
                "Access denied",
                HttpStatus.FORBIDDEN.value(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(body);
    }

}
