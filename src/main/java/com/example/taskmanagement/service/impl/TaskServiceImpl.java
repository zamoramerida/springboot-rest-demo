package com.example.taskmanagement.service.impl;

import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllTasks() {
        logger.debug("Fetching all tasks from repository");
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        logger.debug("Fetching task by ID: {}", id);
        return taskRepository.findById(id);
    }

    @Override
    public Optional<Task> getTaskByUuid(UUID uuid) {
        logger.debug("Fetching task by UUID: {}", uuid);
        return taskRepository.findByUuid(uuid);
    }

    @Override
    public Task createTask(Task task) {
        logger.debug("Creating new task: {}", task);
        Task savedTask = taskRepository.save(task);
        logger.info("Task created successfully with ID: {} and UUID: {}", savedTask.getId(), savedTask.getUuid());
        return savedTask;
    }

    @Override
    public Task updateTask(Long id, Task task) {
        logger.debug("Updating task with ID: {}, new details: {}", id, task);
        return taskRepository.findById(id)
                .map(existingTask -> {
                    logger.debug("Found existing task: {}", existingTask);
                    existingTask.setTitle(task.getTitle());
                    existingTask.setDescription(task.getDescription());
                    existingTask.setDueDate(task.getDueDate());
                    existingTask.setStatus(task.getStatus());
                    Task updatedTask = taskRepository.save(existingTask);
                    logger.info("Task updated successfully: {}", updatedTask);
                    return updatedTask;
                })
                .orElseThrow(() -> {
                    logger.error("Task not found with ID: {}", id);
                    return new RuntimeException("Task not found with id: " + id);
                });
    }

    @Override
    public void deleteTask(Long id) {
        logger.debug("Deleting task with ID: {}", id);
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            logger.info("Task deleted successfully with ID: {}", id);
        } else {
            logger.warn("Attempted to delete non-existent task with ID: {}", id);
        }
    }
} 