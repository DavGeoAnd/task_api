package com.davgeoand.api.task_api.controller;

import com.davgeoand.api.task_api.model.base.User;
import com.davgeoand.api.task_api.model.response.UserResponse;
import com.davgeoand.api.task_api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<Object> postUserRequest(@RequestBody User user) {
        log.info("Posting a new user request");
        UserResponse response = userService.saveUser(user);
        return new ResponseEntity<>(response.publicResponse(), response.getStatus());
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsersRequest() {
        log.info("Getting all users request");
        UserResponse response = userService.findAllUsers();
        return new ResponseEntity<>(response.publicResponse(), response.getStatus());
    }

    @GetMapping(value = "/{userKey}")
    public ResponseEntity<Object> getUserRequest(@PathVariable("userKey") String userKey) {
        log.info("Getting an user request");
        UserResponse response = userService.findUserByKey(userKey);
        return new ResponseEntity<>(response.publicResponse(), response.getStatus());
    }

    @PutMapping
    public ResponseEntity<Object> putUserRequest(@RequestBody User user) {
        log.info("Putting an updated user request");
        UserResponse response = userService.updateUser(user);
        return new ResponseEntity<>(response.publicResponse(), response.getStatus());
    }

    @DeleteMapping(value = "/{userKey}")
    public ResponseEntity<Object> deleteUserRequest(@PathVariable("userKey") String userKey) {
        log.info("Deleting an user request");
        UserResponse response = userService.deleteUserByKey(userKey);
        return new ResponseEntity<>(response.publicResponse(), response.getStatus());
    }
}
