package com.example.demo;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @GetMapping("/api/data")
    public Map<String, String> getData() {
        return Map.of(
                "message", "Microservice is running successfully!",
                "status", "Success");
    }
}
