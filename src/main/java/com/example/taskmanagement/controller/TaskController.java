package com.example.taskmanagement.controller;

import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task Management", description = "Task Management API endpoints")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @Operation(summary = "Get all tasks", description = "Retrieves a list of all tasks")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        logger.info("Fetching all tasks");
        List<Task> tasks = taskService.getAllTasks();
        logger.debug("Retrieved {} tasks", tasks.size());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID", description = "Retrieves a specific task by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the task"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<Task> getTaskById(
            @Parameter(description = "ID of the task to retrieve") @PathVariable Long id) {
        logger.info("Fetching task with ID: {}", id);
        return taskService.getTaskById(id)
                .map(task -> {
                    logger.debug("Found task: {}", task);
                    return ResponseEntity.ok(task);
                })
                .orElseGet(() -> {
                    logger.warn("Task not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Get task by UUID", description = "Retrieves a specific task by its UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the task"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<Task> getTaskByUuid(
            @Parameter(description = "UUID of the task to retrieve") @PathVariable UUID uuid) {
        logger.info("Fetching task with UUID: {}", uuid);
        return taskService.getTaskByUuid(uuid)
                .map(task -> {
                    logger.debug("Found task: {}", task);
                    return ResponseEntity.ok(task);
                })
                .orElseGet(() -> {
                    logger.warn("Task not found with UUID: {}", uuid);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    @Operation(summary = "Create a new task", description = "Creates a new task with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Task created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Task> createTask(
            @Parameter(description = "Task details to create") @Valid @RequestBody Task task) {
        logger.info("Creating new task: {}", task);
        Task createdTask = taskService.createTask(task);
        logger.debug("Created task with ID: {} and UUID: {}", createdTask.getId(), createdTask.getUuid());
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing task", description = "Updates a task with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<Task> updateTask(
            @Parameter(description = "ID of the task to update") @PathVariable Long id,
            @Parameter(description = "Updated task details") @Valid @RequestBody Task task) {
        logger.info("Updating task with ID: {}, new details: {}", id, task);
        Task updatedTask = taskService.updateTask(id, task);
        logger.debug("Updated task: {}", updatedTask);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task", description = "Deletes a task by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "ID of the task to delete") @PathVariable Long id) {
        logger.info("Deleting task with ID: {}", id);
        taskService.deleteTask(id);
        logger.debug("Task deleted successfully");
        return ResponseEntity.noContent().build();
    }
} 