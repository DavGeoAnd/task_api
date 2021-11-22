package com.davgeoand.api.task_api.service;

import audit.Auditor;
import com.davgeoand.api.task_api.model.base.Task;
import com.davgeoand.api.task_api.model.response.TaskResponse;
import com.davgeoand.api.task_api.repository.taskData.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    private void sendToAuditor(String messageType, String message) {
        Auditor.auditMessageString(messageType, message);
    }

    public TaskResponse saveTask(Task task) {
        log.info("Saving a new task");
        TaskResponse response = new TaskResponse();
        try {
            response.addObject(taskRepository.save(task));
            response.setMessage("Saved: " + task.getKey());
            response.setStatus(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
        }
        sendToAuditor("statusMessage", response.getMessage());
        return response;
    }

    public TaskResponse findAllTasks() {
        log.info("Finding all tasks");
        TaskResponse response = new TaskResponse();
        try {
            taskRepository.findAll().forEach(response.getResult()::add);
            response.setMessage("All tasks");
            response.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
        }
        sendToAuditor("statusMessage", response.getMessage());
        return response;
    }

    public TaskResponse findTaskByKey(String taskKey) {
        log.info("Finding task by key");
        TaskResponse response = new TaskResponse();
        try {
            taskRepository.findById(taskKey).ifPresentOrElse((task -> {
                        response.addObject(task);
                        response.setMessage("Found: " + task.getKey());
                        response.setStatus(HttpStatus.OK);
                    }), () -> {
                        response.setMessage("Task does not exist");
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

    public TaskResponse updateTask(Task task) {
        log.info("Updating an existing task");
        TaskResponse response = new TaskResponse();
        try {
            if (taskExists(task.getKey())) {
                response.addObject(taskRepository.save(task));
                response.setMessage("Updated: " + task.getKey());
                response.setStatus(HttpStatus.OK);
            } else {
                response.setMessage("Task does not exist");
                response.setStatus(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
        }
        sendToAuditor("statusMessage", response.getMessage());
        return response;
    }

    private boolean taskExists(String key) {
        return taskRepository.existsById(key);
    }

    public TaskResponse deleteTaskByKey(String taskKey) {
        log.info("Finding task by key");
        TaskResponse response = new TaskResponse();
        try {
            if (taskExists(taskKey)) {
                taskRepository.deleteById(taskKey);
                response.setMessage("Deleted: " + taskKey);
                response.setStatus(HttpStatus.GONE);
            } else {
                response.setMessage("Task does not exist");
                response.setStatus(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
        }
        sendToAuditor("statusMessage", response.getMessage());
        return response;
    }
}