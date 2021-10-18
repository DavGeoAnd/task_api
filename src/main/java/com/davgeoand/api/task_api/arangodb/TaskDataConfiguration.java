package com.davgeoand.api.task_api.arangodb;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.ArangoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableArangoRepositories(basePackages = {"com.davgeoand.api.task_api.repository.taskData"})
public class TaskDataConfiguration implements ArangoConfiguration {
    private final String database;
    private final String user;
    private final String password;
    private final String hosts;
    private final int port;

    public TaskDataConfiguration(@Value("${arangodb.taskData.database}") String database,
                                 @Value("${arangodb.taskData.user}") String user,
                                 @Value("${arangodb.taskData.password}") String password,
                                 @Value("${arangodb.taskData.hosts}") String hosts,
                                 @Value("${arangodb.taskData.port}") int port) {
        this.database = database;
        this.user = user;
        this.password = password;
        this.hosts = hosts;
        this.port = port;
    }

    @Override
    public ArangoDB.Builder arango() {
        return new ArangoDB.Builder().user(user).password(password).host(hosts, port);
    }

    @Override
    public String database() {
        return database;
    }
}
