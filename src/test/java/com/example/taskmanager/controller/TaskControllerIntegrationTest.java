package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.taskmanager.config.SecurityConfig; // We need to import security config
import com.example.taskmanager.security.JwtUtils;
import com.example.taskmanager.service.UserDetailsServiceImpl;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
// Import security configs to apply them to the test context
@Import({SecurityConfig.class, UserDetailsServiceImpl.class, JwtUtils.class})
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    // We also need to mock the user repository because UserDetailsServiceImpl depends on it
    @MockBean
    private com.example.taskmanager.repository.UserRepository userRepository;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser // This annotation provides a mock authenticated user for the test
    void getTaskById_whenTaskExists_shouldReturnTask() throws Exception {
        Task task = new Task();
        task.setId("1");
        task.setTitle("Integration Test Task");
        task.setCompleted(false);

        when(taskService.getTaskById(any(String.class))).thenReturn(task);

        mockMvc.perform(get("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Integration Test Task"));
    }

    @Test
    void getTaskById_whenNotAuthenticated_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // Spring security returns 403 Forbidden by default for unauthenticated
    }
}