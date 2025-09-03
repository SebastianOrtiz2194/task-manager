package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing tasks.
 * This class defines the API endpoints for CRUD operations on tasks.
 * @RestController combines @Controller and @ResponseBody, meaning this class
 * will handle web requests and the return value of methods will be serialized
 * directly to the HTTP response body.
 * @RequestMapping("/api/tasks") maps all requests starting with /api/tasks to this controller.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    // Spring's @Autowired annotation handles dependency injection.
    // It automatically injects an instance of TaskRepository.
    @Autowired
    private TaskRepository taskRepository;

    /**
     * GET /api/tasks - Get all tasks.
     * @return A list of all tasks.
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * GET /api/tasks/{id} - Get a single task by its ID.
     * @param id The ID of the task.
     * @return A ResponseEntity containing the task if found, or 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/tasks - Create a new task.
     * @param task The task object to be created, passed in the request body.
     * @return The created task with a 201 Created status.
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = taskRepository.save(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    /**
     * PUT /api/tasks/{id} - Update an existing task.
     * @param id The ID of the task to update.
     * @param taskDetails The new details for the task, passed in the request body.
     * @return The updated task, or 404 Not Found if the task doesn't exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task taskDetails) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(taskDetails.getTitle());
                    task.setDescription(taskDetails.getDescription());
                    task.setCompleted(taskDetails.isCompleted());
                    Task updatedTask = taskRepository.save(task);
                    return ResponseEntity.ok(updatedTask);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/tasks/{id} - Delete a task.
     * @param id The ID of the task to delete.
     * @return A 204 No Content response if successful, or 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}