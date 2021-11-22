package com.davgeoand.api.task_api.repository.taskData;

import com.arangodb.springframework.repository.ArangoRepository;
import com.davgeoand.api.task_api.model.base.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends ArangoRepository<Task, String> {
}