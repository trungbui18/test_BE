package com.example.tracnghiem.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class ServerController {
    @GetMapping
    public ResponseEntity<Map<String, Object>> checkServer() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "OK");
        status.put("timestamp", LocalDateTime.now());
        status.put("server", "Spring Boot Server");
        return ResponseEntity.ok(status);
    }
}
