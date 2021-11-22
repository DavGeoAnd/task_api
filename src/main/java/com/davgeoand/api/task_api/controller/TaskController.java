package com.davgeoand.api.task_api.controller;

import com.davgeoand.api.task_api.model.base.Task;
import com.davgeoand.api.task_api.model.base.Task;
import com.davgeoand.api.task_api.model.base.Task;
import com.davgeoand.api.task_api.model.response.TaskResponse;
import com.davgeoand.api.task_api.model.response.TaskResponse;
import com.davgeoand.api.task_api.model.response.TaskResponse;
import com.davgeoand.api.task_api.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping
    public ResponseEntity<Object> postTaskRequest(@RequestBody Task task) {
        log.info("Posting a new task request");
        TaskResponse response = taskService.saveTask(task);
        return new ResponseEntity<>(response.publicResponse(), response.getStatus());
    }

    @GetMapping
    public ResponseEntity<Object> getAllTasksRequest() {
        log.info("Getting all tasks request");
        TaskResponse response = taskService.findAllTasks();
        return new ResponseEntity<>(response.publicResponse(), response.getStatus());
    }

    @GetMapping(value = "/{taskKey}")
    public ResponseEntity<Object> getTaskRequest(@PathVariable("taskKey") String taskKey) {
        log.info("Getting an task request");
        TaskResponse response = taskService.findTaskByKey(taskKey);
        return new ResponseEntity<>(response.publicResponse(), response.getStatus());
    }

    @PutMapping
    public ResponseEntity<Object> putTaskRequest(@RequestBody Task task) {
        log.info("Putting an updated task request");
        TaskResponse response = taskService.updateTask(task);
        return new ResponseEntity<>(response.publicResponse(), response.getStatus());
    }

    @DeleteMapping(value = "/{taskKey}")
    public ResponseEntity<Object> deleteTaskRequest(@PathVariable("taskKey") String taskKey) {
        log.info("Deleting an task request");
        TaskResponse response = taskService.deleteTaskByKey(taskKey);
        return new ResponseEntity<>(response.publicResponse(), response.getStatus());
    }
}