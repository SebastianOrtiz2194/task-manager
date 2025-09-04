package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskRequestDTO;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing tasks.
 * The controller now uses a DTO for request bodies and includes the @Valid annotation
 * to trigger automatic validation.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        Task createdTask = taskService.createTask(taskRequestDTO);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        Task updatedTask = taskService.updateTask(id, taskRequestDTO);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
