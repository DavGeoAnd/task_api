package com.davgeoand.api.task_api.arangodb;

import com.arangodb.ArangoDB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component("TaskDataDB")
public class TaskDataHealth implements HealthIndicator {
    private final ArangoDB db;
    private final String database;
    private final String[] collections;

    public TaskDataHealth(@Value("${arangodb.taskData.database}") String database,
                          @Value("${arangodb.taskData.user}") String user,
                          @Value("${arangodb.taskData.password}") String password,
                          @Value("${arangodb.taskData.hosts}") String hosts,
                          @Value("${arangodb.taskData.port}") int port,
                          @Value("${arangodb.taskData.collections}") String collections) {
        db = new ArangoDB.Builder().host(hosts, port).user(user).password(password).build();
        this.database = database;
        this.collections = collections.split(",");
    }

    @Override
    public Health getHealth(boolean includeDetails) {
        return HealthIndicator.super.getHealth(includeDetails);
    }

    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();
        Status status = Status.UP;
        TreeMap<String, Boolean> collectionStatusMap = new TreeMap<>();
        for (String str : collections) {
            collectionStatusMap.put(str, db.db(database).collection(str).exists());
        }
        for (Map.Entry<String, Boolean> entry : collectionStatusMap.entrySet()) {
            if (entry.getValue()) {
                builder.withDetail(entry.getKey(), "connected");
            } else {
                builder.withDetail(entry.getKey(), "not connected");
                status = Status.OUT_OF_SERVICE;
            }
        }
        return builder.status(status).build();
    }
}
