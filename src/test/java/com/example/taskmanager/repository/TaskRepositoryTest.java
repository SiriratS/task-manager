package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for TaskRepository
 * Tests JPA repository operations with in-memory H2 database
 */
@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    private Task testTask;

    @BeforeEach
    void setUp() {
        testTask = new Task("Test Task", "Test Description", false);
    }

    @Test
    void saveTask_ShouldPersistTask() {
        // Act
        Task savedTask = taskRepository.save(testTask);

        // Assert
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getTitle()).isEqualTo("Test Task");
        assertThat(savedTask.getDescription()).isEqualTo("Test Description");
        assertThat(savedTask.isCompleted()).isFalse();
    }

    @Test
    void findById_WhenExists_ShouldReturnTask() {
        // Arrange
        Task savedTask = entityManager.persistAndFlush(testTask);

        // Act
        Optional<Task> foundTask = taskRepository.findById(savedTask.getId());

        // Assert
        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getId()).isEqualTo(savedTask.getId());
        assertThat(foundTask.get().getTitle()).isEqualTo("Test Task");
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmpty() {
        // Act
        Optional<Task> foundTask = taskRepository.findById(999L);

        // Assert
        assertThat(foundTask).isEmpty();
    }

    @Test
    void findAll_ShouldReturnAllTasks() {
        // Arrange
        Task task1 = new Task("Task 1", "Description 1", false);
        Task task2 = new Task("Task 2", "Description 2", true);
        Task task3 = new Task("Task 3", "Description 3", false);

        entityManager.persist(task1);
        entityManager.persist(task2);
        entityManager.persist(task3);
        entityManager.flush();

        // Act
        List<Task> tasks = taskRepository.findAll();

        // Assert
        assertThat(tasks).hasSize(3);
        assertThat(tasks).extracting(Task::getTitle)
                .containsExactlyInAnyOrder("Task 1", "Task 2", "Task 3");
    }

    @Test
    void deleteTask_ShouldRemoveTask() {
        // Arrange
        Task savedTask = entityManager.persistAndFlush(testTask);
        Long taskId = savedTask.getId();

        // Act
        taskRepository.delete(savedTask);
        entityManager.flush();

        // Assert
        Optional<Task> deletedTask = taskRepository.findById(taskId);
        assertThat(deletedTask).isEmpty();
    }

    @Test
    void updateTask_ShouldModifyExistingTask() {
        // Arrange
        Task savedTask = entityManager.persistAndFlush(testTask);
        Long taskId = savedTask.getId();

        // Act
        savedTask.setTitle("Updated Title");
        savedTask.setDescription("Updated Description");
        savedTask.setCompleted(true);
        Task updatedTask = taskRepository.save(savedTask);
        entityManager.flush();

        // Assert
        Optional<Task> foundTask = taskRepository.findById(taskId);
        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getTitle()).isEqualTo("Updated Title");
        assertThat(foundTask.get().getDescription()).isEqualTo("Updated Description");
        assertThat(foundTask.get().isCompleted()).isTrue();
    }
}
