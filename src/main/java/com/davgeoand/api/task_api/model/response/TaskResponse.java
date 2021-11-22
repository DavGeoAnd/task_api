package com.davgeoand.api.task_api.model.response;

import com.davgeoand.api.task_api.model.base.Task;
import com.davgeoand.api.task_api.model.base.User;
import lombok.Data;
import model.response.PublicResponse;
import model.response.Response;

@Data
public class TaskResponse extends Response<Task> {

    @Override
    public PublicResponse<Task> publicResponse() {
        return new PublicResponse<>(message, result) {
        };
    }
}