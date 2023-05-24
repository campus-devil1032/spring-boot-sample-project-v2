package com.example.campusspringdemov2.domain.monitoring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/${api.version.v1}/monitor")
public class HealthCheckController {

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "OK";
    }

}