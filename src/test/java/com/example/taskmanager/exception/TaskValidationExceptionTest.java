package com.example.taskmanager.exception;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class TaskValidationExceptionTest {

    @Test
    void constructor_WithMessage_ShouldSetMessageAndErrorList() {
        // Arrange
        String message = "Validation error";

        // Act
        TaskValidationException exception = new TaskValidationException(message);

        // Assert
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getErrors()).containsExactly(message);
    }

    @Test
    void constructor_WithMessageAndErrors_ShouldSetBoth() {
        // Arrange
        String message = "Validation failed";
        List<String> errors = Arrays.asList("Error 1", "Error 2");

        // Act
        TaskValidationException exception = new TaskValidationException(message, errors);

        // Assert
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getErrors()).isEqualTo(errors);
    }

    @Test
    void constructor_WithErrors_ShouldSetDefaultMessageAndErrors() {
        // Arrange
        List<String> errors = Arrays.asList("Error 1", "Error 2");

        // Act
        TaskValidationException exception = new TaskValidationException(errors);

        // Assert
        assertThat(exception.getMessage()).isEqualTo("Validation failed");
        assertThat(exception.getErrors()).isEqualTo(errors);
    }

    @Test
    void setErrors_ShouldUpdateErrors() {
        // Arrange
        TaskValidationException exception = new TaskValidationException("Initial message");
        List<String> newErrors = Arrays.asList("New Error 1", "New Error 2");

        // Act
        exception.setErrors(newErrors);

        // Assert
        assertThat(exception.getErrors()).isEqualTo(newErrors);
    }
}
