package com.example.taskmanager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;

/**
 * Represents a Task entity.
 * Implements Serializable to allow the object to be sent over Kafka and stored in Redis.
 */
@Document(collection = "tasks")
@Data
public class Task implements Serializable {

    // A version UID for serialization
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String title;
    private String description;
    private boolean completed;
}

