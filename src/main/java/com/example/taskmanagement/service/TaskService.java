package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Task;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> getAllTasks();
    Optional<Task> getTaskById(Long id);
    Optional<Task> getTaskByUuid(UUID uuid);
    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
} 