package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();
    Optional<Task> getTaskById(String id);
    Task createTask(Task task);
    Optional<Task> updateTask(String id, Task taskDetails);
    boolean deleteTask(String id);
}
