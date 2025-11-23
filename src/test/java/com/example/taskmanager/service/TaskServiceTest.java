package com.example.taskmanager.service;

import com.example.taskmanager.exception.ResourceNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TaskService
 * Tests business logic with mocked repository
 */
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task testTask;

    @BeforeEach
    void setUp() {
        testTask = new Task("Test Task", "Test Description", false);
        testTask.setId(1L);
    }

    @Test
    void getAllTasks_ShouldReturnAllTasks() {
        // Arrange
        Task task2 = new Task("Task 2", "Description 2", true);
        task2.setId(2L);
        List<Task> expectedTasks = Arrays.asList(testTask, task2);
        when(taskRepository.findAll()).thenReturn(expectedTasks);

        // Act
        List<Task> actualTasks = taskService.getAllTasks();

        // Assert
        assertThat(actualTasks).hasSize(2);
        assertThat(actualTasks).containsExactly(testTask, task2);
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskById_WhenTaskExists_ShouldReturnTask() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));

        // Act
        Task actualTask = taskService.getTaskById(1L);

        // Assert
        assertThat(actualTask).isNotNull();
        assertThat(actualTask.getId()).isEqualTo(1L);
        assertThat(actualTask.getTitle()).isEqualTo("Test Task");
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTaskById_WhenTaskNotFound_ShouldThrowException() {
        // Arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> taskService.getTaskById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Task not found with id: 999");
        verify(taskRepository, times(1)).findById(999L);
    }

    @Test
    void createTask_ShouldSaveAndReturnTask() {
        // Arrange
        Task newTask = new Task("New Task", "New Description", false);
        Task savedTask = new Task("New Task", "New Description", false);
        savedTask.setId(3L);
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        // Act
        Task actualTask = taskService.createTask(newTask);

        // Assert
        assertThat(actualTask).isNotNull();
        assertThat(actualTask.getId()).isEqualTo(3L);
        assertThat(actualTask.getTitle()).isEqualTo("New Task");
        verify(taskRepository, times(1)).save(newTask);
    }

    @Test
    void updateTask_WhenTaskExists_ShouldUpdateAndReturnTask() {
        // Arrange
        Task updateDetails = new Task("Updated Title", "Updated Description", true);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        // Act
        Task updatedTask = taskService.updateTask(1L, updateDetails);

        // Assert
        assertThat(updatedTask.getTitle()).isEqualTo("Updated Title");
        assertThat(updatedTask.getDescription()).isEqualTo("Updated Description");
        assertThat(updatedTask.isCompleted()).isTrue();
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(testTask);
    }

    @Test
    void updateTask_WhenTaskNotFound_ShouldThrowException() {
        // Arrange
        Task updateDetails = new Task("Updated Title", "Updated Description", true);
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> taskService.updateTask(999L, updateDetails))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Task not found with id: 999");
        verify(taskRepository, times(1)).findById(999L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void deleteTask_WhenTaskExists_ShouldDeleteTask() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));
        doNothing().when(taskRepository).delete(testTask);

        // Act
        taskService.deleteTask(1L);

        // Assert
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).delete(testTask);
    }

    @Test
    void deleteTask_WhenTaskNotFound_ShouldThrowException() {
        // Arrange
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> taskService.deleteTask(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Task not found with id: 999");
        verify(taskRepository, times(1)).findById(999L);
        verify(taskRepository, never()).delete(any(Task.class));
    }
}
