package com.example.taskmanager;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

/**
 * Unit tests for TaskManagerApplication.
 * Tests the Spring Boot application context and main application class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskManagerApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // This test ensures that the Spring application context loads successfully.
        // If there are any configuration errors, bean creation failures, or missing
        // dependencies, this test will fail.
        assertThat(applicationContext).isNotNull();
    }

    @Test
    void applicationContextContainsTaskManagerApplication() {
        // Verify that the main application bean is present in the context
        assertThat(applicationContext.containsBean("taskManagerApplication")).isTrue();
    }

    @Test
    void applicationContextContainsExpectedBeans() {
        // Verify that essential beans are loaded in the application context
        assertThat(applicationContext.containsBean("taskController")).isTrue();
        assertThat(applicationContext.containsBean("taskService")).isTrue();
        assertThat(applicationContext.containsBean("taskRepository")).isTrue();
    }

    @Test
    void main_ShouldInvokeSpringApplicationRun() {
        // Mock the SpringApplication.run() method to avoid actually starting the
        // application
        ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);

        try (MockedStatic<SpringApplication> springApplicationMock = mockStatic(SpringApplication.class)) {
            // Configure the mock to return our mock context
            springApplicationMock
                    .when(() -> SpringApplication.run(eq(TaskManagerApplication.class), any(String[].class)))
                    .thenReturn(mockContext);

            // Call the main method
            TaskManagerApplication.main(new String[] {});

            // Verify that SpringApplication.run was called exactly once with correct
            // arguments
            springApplicationMock.verify(
                    () -> SpringApplication.run(eq(TaskManagerApplication.class), any(String[].class)),
                    times(1));
        }
    }
}
