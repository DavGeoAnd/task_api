package com.davgeoand.api.task_api.service;

import audit.Auditor;
import com.davgeoand.api.task_api.model.base.TaskConnection;
import com.davgeoand.api.task_api.model.response.TaskConnectionResponse;
import com.davgeoand.api.task_api.repository.taskData.TaskConnectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskConnectionService {
    @Autowired
    TaskConnectionRepository taskConnectionConnectionRepository;

    private void sendToAuditor(String messageType, String message) {
        Auditor.auditMessageString(messageType, message);
    }

    public TaskConnectionResponse saveTaskConnection(TaskConnection taskConnection) {
        log.info("Saving a new taskConnection");
        TaskConnectionResponse response = new TaskConnectionResponse();
        try {
            response.addObject(taskConnectionConnectionRepository.save(taskConnection));
            response.setMessage("Saved: " + taskConnection.getKey());
            response.setStatus(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
        }
        sendToAuditor("statusMessage", response.getMessage());
        return response;
    }

    public TaskConnectionResponse findAllTaskConnections() {
        log.info("Finding all taskConnections");
        TaskConnectionResponse response = new TaskConnectionResponse();
        try {
            taskConnectionConnectionRepository.findAll().forEach(response.getResult()::add);
            response.setMessage("All taskConnections");
            response.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
        }
        sendToAuditor("statusMessage", response.getMessage());
        return response;
    }

    public TaskConnectionResponse findTaskConnectionByKey(String taskConnectionKey) {
        log.info("Finding taskConnection by key");
        TaskConnectionResponse response = new TaskConnectionResponse();
        try {
            taskConnectionConnectionRepository.findById(taskConnectionKey).ifPresentOrElse((taskConnection -> {
                        response.addObject(taskConnection);
                        response.setMessage("Found: " + taskConnection.getKey());
                        response.setStatus(HttpStatus.OK);
                    }), () -> {
                        response.setMessage("TaskConnection does not exist");
                        response.setStatus(HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
        }
        sendToAuditor("statusMessage", response.getMessage());
        return response;
    }

    public TaskConnectionResponse updateTaskConnection(TaskConnection taskConnection) {
        log.info("Updating an existing taskConnection");
        TaskConnectionResponse response = new TaskConnectionResponse();
        try {
            if (taskConnectionExists(taskConnection.getKey())) {
                response.addObject(taskConnectionConnectionRepository.save(taskConnection));
                response.setMessage("Updated: " + taskConnection.getKey());
                response.setStatus(HttpStatus.OK);
            } else {
                response.setMessage("TaskConnection does not exist");
                response.setStatus(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
        }
        sendToAuditor("statusMessage", response.getMessage());
        return response;
    }

    private boolean taskConnectionExists(String key) {
        return taskConnectionConnectionRepository.existsById(key);
    }

    public TaskConnectionResponse deleteTaskConnectionByKey(String taskConnectionKey) {
        log.info("Finding taskConnection by key");
        TaskConnectionResponse response = new TaskConnectionResponse();
        try {
            if (taskConnectionExists(taskConnectionKey)) {
                taskConnectionConnectionRepository.deleteById(taskConnectionKey);
                response.setMessage("Deleted: " + taskConnectionKey);
                response.setStatus(HttpStatus.GONE);
            } else {
                response.setMessage("TaskConnection does not exist");
                response.setStatus(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
        }
        sendToAuditor("statusMessage", response.getMessage());
        return response;
    }

    public TaskConnectionResponse findAllOwnTasks() {
        log.info("Finding all ownTasks");
        TaskConnectionResponse response = new TaskConnectionResponse();
        try {
            taskConnectionConnectionRepository.findAllOwnTasks().forEach(response.getResult()::add);
            response.setMessage("All ownTasks");
            response.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
        }
        sendToAuditor("statusMessage", response.getMessage());
        return response;
    }

    public TaskConnectionResponse findAllShareTasks() {
        log.info("Finding all shareTasks");
        TaskConnectionResponse response = new TaskConnectionResponse();
        try {
            taskConnectionConnectionRepository.findAllShareTasks().forEach(response.getResult()::add);
            response.setMessage("All shareTasks");
            response.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
        }
        sendToAuditor("statusMessage", response.getMessage());
        return response;
    }

    public TaskConnectionResponse findAllTaskConnectionsForUser(String userKey) {
        log.info("Finding all taskConnections for user");
        TaskConnectionResponse response = new TaskConnectionResponse();
        try {
            taskConnectionConnectionRepository.findAllForUser("users/" + userKey).forEach(response.getResult()::add);
            response.setMessage("All shareTasks for user");
            response.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
        }
        sendToAuditor("statusMessage", response.getMessage());
        return response;
    }
}

