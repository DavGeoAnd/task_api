package com.davgeoand.api.task_api;

import actuator.AppMetrics;
import audit.AppInterceptor;
import audit.Auditor;
import audit.InterceptorAppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@PropertySource("apiBase.properties")
@Import({AppInterceptor.class, InterceptorAppConfig.class, AppMetrics.class, Auditor.class})
@SpringBootApplication
@RestController
public class TaskApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskApiApplication.class, args);
    }

    @GetMapping("/")
    public ResponseEntity<Object> home() {
        return new ResponseEntity<>("Task API Running", HttpStatus.OK);
    }
}
