package com.example.taskmanager.service;

import com.example.taskmanager.event.TaskEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Service that listens for messages from Kafka.
 * This simulates a separate service or component reacting to task events.
 */
@Service
public class KafkaConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "task-events", groupId = "task-manager-group")
    public void consume(TaskEvent event) {
        LOGGER.info(String.format("Consumed message <- %s", event));

        // Here you could add logic to handle the event, for example:
        // - Send an email or push notification
        // - Update a search index
        // - Trigger a data analytics pipeline
        switch (event.getType()) {
            case CREATED:
                LOGGER.info("A new task was created: " + event.getTask().getTitle());
                break;
            case UPDATED:
                LOGGER.info("Task was updated: " + event.getTask().getTitle());
                break;
            case DELETED:
                LOGGER.info("Task was deleted: " + event.getTask().getTitle());
                break;
        }
    }
}
