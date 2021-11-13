package com.davgeoand.api.task_api.service;

import audit.Auditor;
import com.davgeoand.api.task_api.model.base.User;
import com.davgeoand.api.task_api.model.response.UserResponse;
import com.davgeoand.api.task_api.repository.taskData.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    private void sendToAuditor(String messageType, String message) {
        Auditor.auditMessageString(messageType, message);
    }

    public UserResponse saveUser(User user) {
        log.info("Saving a new user");
        UserResponse response = new UserResponse();
        try {
            response.addObject(userRepository.save(user));
            response.setMessage("Saved: " + user.getKey());
            response.setStatus(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
        }
        sendToAuditor("statusMessage", response.getMessage());
        return response;
    }

    public UserResponse findAllUsers() {
        log.info("Finding all users");
        UserResponse response = new UserResponse();
        try {
            userRepository.findAll().forEach(response.getResult()::add);
            response.setMessage("All users");
            response.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
        }
        sendToAuditor("statusMessage", response.getMessage());
        return response;
    }

    public UserResponse findUserByKey(String userKey) {
        log.info("Finding user by key");
        UserResponse response = new UserResponse();
        try {
            userRepository.findById(userKey).ifPresentOrElse((user -> {
                        response.addObject(user);
                        response.setMessage("Found: " + user.getKey());
                        response.setStatus(HttpStatus.OK);
                    }), () -> {
                        response.setMessage("User does not exist");
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

    public UserResponse updateUser(User user) {
        log.info("Updating an existing user");
        UserResponse response = new UserResponse();
        try {
            if (userExists(user.getKey())) {
                response.addObject(userRepository.save(user));
                response.setMessage("Updated: " + user.getKey());
                response.setStatus(HttpStatus.OK);
            } else {
                response.setMessage("User does not exist");
                response.setStatus(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMessage(e.getMessage());
        }
        sendToAuditor("statusMessage", response.getMessage());
        return response;
    }

    private boolean userExists(String key) {
        return userRepository.existsById(key);
    }

    public UserResponse deleteUserByKey(String userKey) {
        log.info("Finding user by key");
        UserResponse response = new UserResponse();
        try {
            if (userExists(userKey)) {
                userRepository.deleteById(userKey);
                response.setMessage("Deleted: " + userKey);
                response.setStatus(HttpStatus.GONE);
            } else {
                response.setMessage("User does not exist");
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
