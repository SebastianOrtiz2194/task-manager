package com.example.taskmanager.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuration class to define Kafka topics.
 * This ensures that the topic is created on the Kafka broker when the application starts.
 */
@Configuration
public class KafkaTopicConfig {

    public static final String TASKS_TOPIC = "task-events";

    @Bean
    public NewTopic tasksTopic() {
        return TopicBuilder.name(TASKS_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
