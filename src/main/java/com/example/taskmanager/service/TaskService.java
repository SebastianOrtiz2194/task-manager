package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequestDTO;
import com.example.taskmanager.model.Task;
import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();
    Task getTaskById(String id);
    Task createTask(TaskRequestDTO taskRequestDTO);
    Task updateTask(String id, TaskRequestDTO taskRequestDTO);
    void deleteTask(String id);
}
