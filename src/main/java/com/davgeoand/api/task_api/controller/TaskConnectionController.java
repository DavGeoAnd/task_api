package com.davgeoand.api.task_api.controller;

import com.davgeoand.api.task_api.model.base.TaskConnection;
import com.davgeoand.api.task_api.model.response.TaskConnectionResponse;
import com.davgeoand.api.task_api.service.TaskConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/taskConnections")
public class TaskConnectionController {
    @Autowired
    TaskConnectionService taskConnectionService;

    @PostMapping
    public ResponseEntity<Object> postTaskConnectionRequest(@RequestBody TaskConnection taskConnection) {
        log.info("Posting a new taskConnection request");
        TaskConnectionResponse response = taskConnectionService.saveTaskConnection(taskConnection);
        return new ResponseEntity<>(response.publicResponse(), response.getStatus());
    }

    @GetMapping
    public ResponseEntity<Object> getAllTaskConnectionsRequest() {
        log.info("Getting all taskConnections request");
        TaskConnectionResponse response = taskConnectionService.findAllTaskConnections();
        return new ResponseEntity<>(response.publicResponse(), response.getStatus());
    }

    @GetMapping(value = "/ownTasks")
    public ResponseEntity<Object> getAllOwnTasksRequest() {
        log.info("Getting all ownTasks request");
        TaskConnectionResponse response = taskConnectionService.findAllOwnTasks();
        return new ResponseEntity<>(response.publicResponse(), response.getStatus());
    }

    @GetMapping(value = "/shareTasks")
    public ResponseEntity<Object> getAllShareTasksRequest() {
        log.info("Getting all shareTasks request");
        TaskConnectionResponse response = taskConnectionService.findAllShareTasks();
        return new ResponseEntity<>(response.publicResponse(), response.getStatus());
    }

    @GetMapping(value = "/user/{userKey}")
    public ResponseEntity<Object> getAllTaskConnectionsForUserRequest(@PathVariable("userKey") String userKey) {
        log.info("Get all taskConnections for user by key request");
        TaskConnectionResponse response = taskConnectionService.findAllTaskConnectionsForUser(userKey);
        return new ResponseEntity<>(response.publicResponse(), response.getStatus());
    }

    @PutMapping
    public ResponseEntity<Object> putTaskConnectionRequest(@RequestBody TaskConnection taskConnection) {
        log.info("Putting an updated taskConnection request");
        TaskConnectionResponse response = taskConnectionService.updateTaskConnection(taskConnection);
        return new ResponseEntity<>(response.publicResponse(), response.getStatus());
    }
}
