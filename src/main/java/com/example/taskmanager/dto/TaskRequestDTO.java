package com.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object for creating and updating tasks.
 * This object carries the data from the client to the application and includes validation rules.
 */
@Data
public class TaskRequestDTO {

    @NotBlank(message = "Title cannot be blank.")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters.")
    private String title;

    @Size(max = 500, message = "Description can be up to 500 characters.")
    private String description;

    @NotNull(message = "Completed status cannot be null.")
    private Boolean completed;
}