package com.example.taskmanager.service;

import com.example.taskmanager.config.KafkaTopicConfig;
import com.example.taskmanager.event.TaskEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service responsible for sending messages to Kafka.
 */
@Service
public class KafkaProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, TaskEvent> kafkaTemplate;

    public void sendMessage(TaskEvent event) {
        LOGGER.info(String.format("Producing message -> %s", event));
        this.kafkaTemplate.send(KafkaTopicConfig.TASKS_TOPIC, event);
    }
}