package com.davgeoand.api.task_api.actuator;

import actuator.AppMetricExportAutoConfiguration;
import io.micrometer.core.instrument.Gauge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class DbMetrics {
    @Autowired
    HealthEndpoint healthEndpoint;

    @PostConstruct
    public void addDbMetrics() {
        log.info("Adding task data database metric");
        Gauge.builder("task.data.db.health", () -> healthEndpoint.healthForPath("TaskDataDB").getStatus() == Status.UP ? 1 : 0)
                .description("task data db connection status").register(AppMetricExportAutoConfiguration.getAppMetricMeterRegistry());
    }
}
