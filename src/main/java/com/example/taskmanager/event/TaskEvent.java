package com.example.taskmanager.event;

import com.example.taskmanager.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for carrying task event information over Kafka.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskEvent {
    private EventType type;
    private Task task;

    public enum EventType {
        CREATED, UPDATED, DELETED
    }
}