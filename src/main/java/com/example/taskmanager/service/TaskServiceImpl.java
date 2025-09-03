package com.example.taskmanager.service;

import com.example.taskmanager.event.TaskEvent;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation containing the business logic for managing tasks.
 * This class integrates the database repository, caching, and Kafka producer.
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    @Cacheable(value = "tasks", key = "#id")
    public Optional<Task> getTaskById(String id) {
        // This method will first check the 'tasks' cache.
        // If an entry with key 'id' is found, it's returned.
        // Otherwise, this method is executed, and its result is cached.
        return taskRepository.findById(id);
    }

    @Override
    public Task createTask(Task task) {
        Task savedTask = taskRepository.save(task);
        // Publish an event to Kafka
        kafkaProducerService.sendMessage(new TaskEvent(TaskEvent.EventType.CREATED, savedTask));
        return savedTask;
    }

    @Override
    @CachePut(value = "tasks", key = "#id")
    public Optional<Task> updateTask(String id, Task taskDetails) {
        // @CachePut always executes the method and updates the cache with the result.
        // This keeps the cache entry fresh after an update.
        return taskRepository.findById(id).map(task -> {
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setCompleted(taskDetails.isCompleted());
            Task updatedTask = taskRepository.save(task);
            // Publish an event to Kafka
            kafkaProducerService.sendMessage(new TaskEvent(TaskEvent.EventType.UPDATED, updatedTask));
            return updatedTask;
        });
    }

    @Override
    @CacheEvict(value = "tasks", key = "#id")
    public boolean deleteTask(String id) {
        // @CacheEvict removes the entry from the cache.
        return taskRepository.findById(id).map(task -> {
            taskRepository.delete(task);
            // Publish an event to Kafka
            kafkaProducerService.sendMessage(new TaskEvent(TaskEvent.EventType.DELETED, task));
            return true;
        }).orElse(false);
    }
}