package com.scania.ZooAssignment.controller.HealthCheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
class StatusController {
    @GetMapping
    public String statusCheck(){
        return "Server is running";
    }
}
