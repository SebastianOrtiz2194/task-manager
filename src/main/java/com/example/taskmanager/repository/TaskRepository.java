package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Task documents.
 * It extends MongoRepository, which provides CRUD operations for the Task entity.
 * Spring Data MongoDB will automatically implement this repository interface.
 * The first generic type is the domain class (Task) and the second is the ID type (String).
 */
@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

}
