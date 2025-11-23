package com.example.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Generic API response wrapper.
 *
 * @param <T> the type of data in the response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;

    public ApiResponse() {
    }

    /**
     * Constructs an ApiResponse with status and message.
     *
     * @param status  the response status
     * @param message the response message
     */
    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Constructs an ApiResponse with status, message, and data.
     *
     * @param status  the response status
     * @param message the response message
     * @param data    the response data
     */
    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>("success", message);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("success", message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>("error", message);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
