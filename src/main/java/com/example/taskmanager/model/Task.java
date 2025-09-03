package com.example.taskmanager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a Task entity.
 * The @Document annotation marks this class as a MongoDB document.
 * The 'tasks' value is the name of the collection in MongoDB.
 * The @Data annotation from Lombok automatically generates getters, setters,
 * toString(), equals(), and hashCode() methods.
 * The @Id annotation marks the 'id' field as the primary key.
 */
@Document(collection = "tasks")
@Data
public class Task {

    @Id
    private String id;
    private String title;
    private String description;
    private boolean completed;
}
