package com.davgeoand.api.task_api.model.base;

import com.arangodb.springframework.annotation.*;
import com.davgeoand.api.task_api.model.taskConnection.OwnTask;
import com.davgeoand.api.task_api.model.taskConnection.ShareTask;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = OwnTask.class, name = "OwnTask"),
        @JsonSubTypes.Type(value = ShareTask.class, name = "ShareTask")})
@Edge("taskConnections")
public class TaskConnection {
    @ArangoId
    protected String id;
    @Id
    protected String key;
    @Rev
    protected String rev;
    @From
    protected User from;
    @To
    protected Task to;
    protected String note;
}