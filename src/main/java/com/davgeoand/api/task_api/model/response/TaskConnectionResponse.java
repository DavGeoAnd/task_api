package com.davgeoand.api.task_api.model.response;

import com.davgeoand.api.task_api.model.base.TaskConnection;
import com.davgeoand.api.task_api.model.base.User;
import lombok.Data;
import model.response.PublicResponse;
import model.response.Response;

@Data
public class TaskConnectionResponse extends Response<TaskConnection> {

    @Override
    public PublicResponse<TaskConnection> publicResponse() {
        return new PublicResponse<>(message, result) {
        };
    }
}