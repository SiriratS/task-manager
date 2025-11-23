package com.example.taskmanager.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for ApiResponse DTO.
 */
class ApiResponseTest {

    @Test
    void defaultConstructor_ShouldCreateEmptyApiResponse() {
        // Given & When
        ApiResponse<String> apiResponse = new ApiResponse<>();

        // Then
        assertThat(apiResponse.getStatus()).isNull();
        assertThat(apiResponse.getMessage()).isNull();
        assertThat(apiResponse.getData()).isNull();
    }

    @Test
    void constructorWithStatusAndMessage_ShouldSetFields() {
        // Given
        String status = "success";
        String message = "Operation completed";

        // When
        ApiResponse<String> apiResponse = new ApiResponse<>(status, message);

        // Then
        assertThat(apiResponse.getStatus()).isEqualTo(status);
        assertThat(apiResponse.getMessage()).isEqualTo(message);
        assertThat(apiResponse.getData()).isNull();
    }

    @Test
    void constructorWithStatusMessageAndData_ShouldSetAllFields() {
        // Given
        String status = "success";
        String message = "Task created";
        String data = "Task ID: 123";

        // When
        ApiResponse<String> apiResponse = new ApiResponse<>(status, message, data);

        // Then
        assertThat(apiResponse.getStatus()).isEqualTo(status);
        assertThat(apiResponse.getMessage()).isEqualTo(message);
        assertThat(apiResponse.getData()).isEqualTo(data);
    }

    @Test
    void successStaticMethod_ShouldCreateSuccessResponse() {
        // Given
        String message = "Operation successful";

        // When
        ApiResponse<String> apiResponse = ApiResponse.success(message);

        // Then
        assertThat(apiResponse.getStatus()).isEqualTo("success");
        assertThat(apiResponse.getMessage()).isEqualTo(message);
        assertThat(apiResponse.getData()).isNull();
    }

    @Test
    void successStaticMethodWithData_ShouldCreateSuccessResponseWithData() {
        // Given
        String message = "Task retrieved";
        Integer taskId = 42;

        // When
        ApiResponse<Integer> apiResponse = ApiResponse.success(message, taskId);

        // Then
        assertThat(apiResponse.getStatus()).isEqualTo("success");
        assertThat(apiResponse.getMessage()).isEqualTo(message);
        assertThat(apiResponse.getData()).isEqualTo(taskId);
    }

    @Test
    void errorStaticMethod_ShouldCreateErrorResponse() {
        // Given
        String message = "Operation failed";

        // When
        ApiResponse<String> apiResponse = ApiResponse.error(message);

        // Then
        assertThat(apiResponse.getStatus()).isEqualTo("error");
        assertThat(apiResponse.getMessage()).isEqualTo(message);
        assertThat(apiResponse.getData()).isNull();
    }

    @Test
    void setStatus_ShouldUpdateStatus() {
        // Given
        ApiResponse<String> apiResponse = new ApiResponse<>();

        // When
        apiResponse.setStatus("pending");

        // Then
        assertThat(apiResponse.getStatus()).isEqualTo("pending");
    }

    @Test
    void setMessage_ShouldUpdateMessage() {
        // Given
        ApiResponse<String> apiResponse = new ApiResponse<>();
        String newMessage = "Updated message";

        // When
        apiResponse.setMessage(newMessage);

        // Then
        assertThat(apiResponse.getMessage()).isEqualTo(newMessage);
    }

    @Test
    void setData_ShouldUpdateData() {
        // Given
        ApiResponse<String> apiResponse = new ApiResponse<>();
        String data = "Some data";

        // When
        apiResponse.setData(data);

        // Then
        assertThat(apiResponse.getData()).isEqualTo(data);
    }

    @Test
    void apiResponseWithComplexDataType_ShouldHandleCorrectly() {
        // Given
        String message = "User data retrieved";
        TestUser user = new TestUser("John Doe", "john@example.com");

        // When
        ApiResponse<TestUser> apiResponse = ApiResponse.success(message, user);

        // Then
        assertThat(apiResponse.getStatus()).isEqualTo("success");
        assertThat(apiResponse.getMessage()).isEqualTo(message);
        assertThat(apiResponse.getData()).isNotNull();
        assertThat(apiResponse.getData().getName()).isEqualTo("John Doe");
        assertThat(apiResponse.getData().getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void apiResponseWithNullData_ShouldHandleGracefully() {
        // Given
        String message = "No data available";

        // When
        ApiResponse<String> apiResponse = ApiResponse.success(message, null);

        // Then
        assertThat(apiResponse.getStatus()).isEqualTo("success");
        assertThat(apiResponse.getMessage()).isEqualTo(message);
        assertThat(apiResponse.getData()).isNull();
    }

    // Helper class for testing complex data types
    private static class TestUser {
        private String name;
        private String email;

        public TestUser(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }
}
