package com.davgeoand.api.task_api;

import actuator.AppDetails;
import audit.AppInterceptor;
import audit.AuditKafkaConfig;
import audit.Auditor;
import audit.InterceptorAppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@PropertySource("apiBase.properties")
@Import({AppInterceptor.class, Auditor.class, AppDetails.class})
@ImportAutoConfiguration({InterceptorAppConfig.class, AuditKafkaConfig.class})
@SpringBootApplication
@RestController
public class TaskApiApplication {
    //-Dspring.profiles.active=test -Xms1G -Xmx1G
    public static void main(String[] args) {
        SpringApplication.run(TaskApiApplication.class, args);
    }

    @GetMapping("/")
    public ResponseEntity<Object> home() {
        return new ResponseEntity<>("Task API Running", HttpStatus.OK);
    }
}
