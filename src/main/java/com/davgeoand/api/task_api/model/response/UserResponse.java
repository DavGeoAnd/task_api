package com.davgeoand.api.task_api.model.response;

import com.davgeoand.api.task_api.model.base.User;
import lombok.Data;
import model.response.PublicResponse;
import model.response.Response;

@Data
public class UserResponse extends Response<User> {

    @Override
    public PublicResponse<User> publicResponse() {
        return new PublicResponse<>(message, result) {
        };
    }
}
