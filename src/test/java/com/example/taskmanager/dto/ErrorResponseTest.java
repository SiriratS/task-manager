package com.example.taskmanager.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for ErrorResponse DTO.
 */
class ErrorResponseTest {

    @Test
    void defaultConstructor_ShouldSetTimestamp() {
        // Given & When
        ErrorResponse errorResponse = new ErrorResponse();

        // Then
        assertThat(errorResponse.getTimestamp()).isNotNull();
        assertThat(errorResponse.getTimestamp()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    void constructorWithMessage_ShouldSetStatusMessageAndTimestamp() {
        // Given
        String message = "An error occurred";

        // When
        ErrorResponse errorResponse = new ErrorResponse(message);

        // Then
        assertThat(errorResponse.getStatus()).isEqualTo("error");
        assertThat(errorResponse.getMessage()).isEqualTo(message);
        assertThat(errorResponse.getTimestamp()).isNotNull();
        assertThat(errorResponse.getErrors()).isNull();
    }

    @Test
    void constructorWithMessageAndErrors_ShouldSetAllFields() {
        // Given
        String message = "Validation failed";
        List<String> errors = Arrays.asList("Field 'name' is required", "Field 'email' is invalid");

        // When
        ErrorResponse errorResponse = new ErrorResponse(message, errors);

        // Then
        assertThat(errorResponse.getStatus()).isEqualTo("error");
        assertThat(errorResponse.getMessage()).isEqualTo(message);
        assertThat(errorResponse.getErrors()).hasSize(2);
        assertThat(errorResponse.getErrors()).containsExactly("Field 'name' is required", "Field 'email' is invalid");
        assertThat(errorResponse.getTimestamp()).isNotNull();
    }

    @Test
    void setStatus_ShouldUpdateStatus() {
        // Given
        ErrorResponse errorResponse = new ErrorResponse();

        // When
        errorResponse.setStatus("warning");

        // Then
        assertThat(errorResponse.getStatus()).isEqualTo("warning");
    }

    @Test
    void setMessage_ShouldUpdateMessage() {
        // Given
        ErrorResponse errorResponse = new ErrorResponse();
        String newMessage = "Updated error message";

        // When
        errorResponse.setMessage(newMessage);

        // Then
        assertThat(errorResponse.getMessage()).isEqualTo(newMessage);
    }

    @Test
    void setErrors_ShouldUpdateErrors() {
        // Given
        ErrorResponse errorResponse = new ErrorResponse();
        List<String> errors = Arrays.asList("Error 1", "Error 2");

        // When
        errorResponse.setErrors(errors);

        // Then
        assertThat(errorResponse.getErrors()).hasSize(2);
        assertThat(errorResponse.getErrors()).containsExactly("Error 1", "Error 2");
    }

    @Test
    void setTimestamp_ShouldUpdateTimestamp() {
        // Given
        ErrorResponse errorResponse = new ErrorResponse();
        LocalDateTime customTimestamp = LocalDateTime.of(2025, 11, 23, 10, 30);

        // When
        errorResponse.setTimestamp(customTimestamp);

        // Then
        assertThat(errorResponse.getTimestamp()).isEqualTo(customTimestamp);
    }

    @Test
    void errorResponseWithEmptyErrorsList_ShouldHandleGracefully() {
        // Given
        String message = "Error with no details";
        List<String> emptyErrors = Arrays.asList();

        // When
        ErrorResponse errorResponse = new ErrorResponse(message, emptyErrors);

        // Then
        assertThat(errorResponse.getErrors()).isEmpty();
        assertThat(errorResponse.getMessage()).isEqualTo(message);
    }
}
