package com.example.taskmanager.exception;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ResourceNotFoundExceptionTest {

    @Test
    void constructor_WithMessage_ShouldSetMessage() {
        // Arrange
        String message = "Custom error message";

        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        // Assert
        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    void constructor_WithResourceNameAndId_ShouldFormatMessage() {
        // Arrange
        String resourceName = "Task";
        Long id = 123L;

        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(resourceName, id);

        // Assert
        assertThat(exception.getMessage()).isEqualTo("Task not found with id: 123");
    }
}
