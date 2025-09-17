package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequestDTO;
import com.example.taskmanager.exception.ResourceNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskRequestDTO taskRequestDTO;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId("1");
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setCompleted(false);

        taskRequestDTO = new TaskRequestDTO();
        taskRequestDTO.setTitle("Test Task");
        taskRequestDTO.setDescription("Test Description");
        taskRequestDTO.setCompleted(false);
    }

    @Test
    void getTaskById_whenTaskExists_shouldReturnTask() {
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById("1");

        assertNotNull(foundTask);
        assertEquals("Test Task", foundTask.getTitle());
        verify(taskRepository, times(1)).findById("1");
    }

    @Test
    void getTaskById_whenTaskDoesNotExist_shouldThrowResourceNotFoundException() {
        when(taskRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.getTaskById("1");
        });

        verify(taskRepository, times(1)).findById("1");
    }

    @Test
    void createTask_shouldSaveAndReturnTaskAndSendMessage() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        doNothing().when(kafkaProducerService).sendMessage(any());

        Task createdTask = taskService.createTask(taskRequestDTO);

        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(kafkaProducerService, times(1)).sendMessage(any());
    }
}
