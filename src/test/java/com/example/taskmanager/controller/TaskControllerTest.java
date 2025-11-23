package com.example.taskmanager.controller;

import com.example.taskmanager.exception.ResourceNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for TaskController
 * Tests REST API endpoints with mocked service layer
 */
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    private Task testTask;

    @BeforeEach
    void setUp() {
        testTask = new Task("Test Task", "Test Description", false);
        testTask.setId(1L);
    }

    @Test
    void getAllTasks_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        Task task2 = new Task("Task 2", "Description 2", true);
        task2.setId(2L);
        List<Task> tasks = Arrays.asList(testTask, task2);
        when(taskService.getAllTasks()).thenReturn(tasks);

        // Act & Assert
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.message", is("Tasks retrieved successfully")))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", is(1)))
                .andExpect(jsonPath("$.data[0].title", is("Test Task")))
                .andExpect(jsonPath("$.data[1].id", is(2)))
                .andExpect(jsonPath("$.data[1].title", is("Task 2")));

        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void getTaskById_WhenExists_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        when(taskService.getTaskById(1L)).thenReturn(testTask);

        // Act & Assert
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.message", is("Task retrieved successfully")))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.title", is("Test Task")))
                .andExpect(jsonPath("$.data.description", is("Test Description")))
                .andExpect(jsonPath("$.data.completed", is(false)));

        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void getTaskById_WhenNotFound_ShouldReturn404() throws Exception {
        // Arrange
        when(taskService.getTaskById(999L))
                .thenThrow(new ResourceNotFoundException("Task", 999L));

        // Act & Assert
        mockMvc.perform(get("/tasks/999"))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).getTaskById(999L);
    }

    @Test
    void createTask_WithValidData_ShouldReturn201() throws Exception {
        // Arrange
        Task newTask = new Task("New Task", "New Description", false);
        Task savedTask = new Task("New Task", "New Description", false);
        savedTask.setId(3L);
        when(taskService.createTask(any(Task.class))).thenReturn(savedTask);

        // Act & Assert
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.message", is("Task created successfully")));

        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    void createTask_WithInvalidData_ShouldReturn400() throws Exception {
        // Arrange - Task with title too short (less than 3 characters)
        Task invalidTask = new Task("AB", "Description", false);

        // Act & Assert
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidTask)))
                .andExpect(status().isBadRequest());

        verify(taskService, never()).createTask(any(Task.class));
    }

    @Test
    void createTask_WithBlankTitle_ShouldReturn400() throws Exception {
        // Arrange - Task with blank title
        Task invalidTask = new Task("", "Description", false);

        // Act & Assert
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidTask)))
                .andExpect(status().isBadRequest());

        verify(taskService, never()).createTask(any(Task.class));
    }

    @Test
    void updateTask_WithValidData_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        Task updateDetails = new Task("Updated Task", "Updated Description", true);
        Task updatedTask = new Task("Updated Task", "Updated Description", true);
        updatedTask.setId(1L);
        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(updatedTask);

        // Act & Assert
        mockMvc.perform(put("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.message", is("Task updated successfully")));

        verify(taskService, times(1)).updateTask(eq(1L), any(Task.class));
    }

    @Test
    void updateTask_WhenNotFound_ShouldReturn404() throws Exception {
        // Arrange
        Task updateDetails = new Task("Updated Task", "Updated Description", true);
        when(taskService.updateTask(eq(999L), any(Task.class)))
                .thenThrow(new ResourceNotFoundException("Task", 999L));

        // Act & Assert
        mockMvc.perform(put("/tasks/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDetails)))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).updateTask(eq(999L), any(Task.class));
    }

    @Test
    void deleteTask_WhenExists_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        doNothing().when(taskService).deleteTask(1L);

        // Act & Assert
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.message", is("Task deleted successfully")));

        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    void deleteTask_WhenNotFound_ShouldReturn404() throws Exception {
        // Arrange
        doThrow(new ResourceNotFoundException("Task", 999L))
                .when(taskService).deleteTask(999L);

        // Act & Assert
        mockMvc.perform(delete("/tasks/999"))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).deleteTask(999L);
    }
}
