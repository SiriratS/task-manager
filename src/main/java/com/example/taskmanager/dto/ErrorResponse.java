package com.example.taskmanager.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for API error responses.
 */
public class ErrorResponse {
    private String status;
    private String message;
    private List<String> errors;
    private LocalDateTime timestamp;

    /**
     * Default constructor.
     */
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructs an ErrorResponse with a message and list of errors.
     *
     * @param message the error message
     * @param errors  the list of errors
     */
    public ErrorResponse(String message, List<String> errors) {
        this.status = "error";
        this.message = message;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructs an ErrorResponse with a message.
     *
     * @param message the error message
     */
    public ErrorResponse(String message) {
        this.status = "error";
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
