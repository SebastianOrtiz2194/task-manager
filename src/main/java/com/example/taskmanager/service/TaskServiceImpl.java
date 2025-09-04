package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequestDTO;
import com.example.taskmanager.event.TaskEvent;
import com.example.taskmanager.exception.ResourceNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation now throws a ResourceNotFoundException when a task
 * is not found, ensuring the global handler can catch it.
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
    public Task getTaskById(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    @Override
    public Task createTask(TaskRequestDTO taskRequestDTO) {
        Task task = new Task();
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setCompleted(taskRequestDTO.getCompleted());

        Task savedTask = taskRepository.save(task);
        kafkaProducerService.sendMessage(new TaskEvent(TaskEvent.EventType.CREATED, savedTask));
        return savedTask;
    }

    @Override
    @CachePut(value = "tasks", key = "#id")
    public Task updateTask(String id, TaskRequestDTO taskRequestDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setCompleted(taskRequestDTO.getCompleted());

        Task updatedTask = taskRepository.save(task);
        kafkaProducerService.sendMessage(new TaskEvent(TaskEvent.EventType.UPDATED, updatedTask));
        return updatedTask;
    }

    @Override
    @CacheEvict(value = "tasks", key = "#id")
    public void deleteTask(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        taskRepository.delete(task);
        kafkaProducerService.sendMessage(new TaskEvent(TaskEvent.EventType.DELETED, task));
    }
}