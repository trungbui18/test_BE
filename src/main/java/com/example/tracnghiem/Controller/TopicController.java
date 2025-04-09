package com.example.tracnghiem.Controller;

import com.example.tracnghiem.Model.Topic;
import com.example.tracnghiem.Service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/topic")
@Tag(name = "Topic Controller", description = "Operations pertaining to topic")
public class TopicController {
    private final TopicService topicService;
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }
    @GetMapping
    @Operation(summary = "Get user by ID",
            description = "Returns a single user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully getAll"),
                    @ApiResponse(responseCode = "400", description = "User not found")
            })
    public ResponseEntity<?> getTopics() {
        try {
            List<Topic> topicList = topicService.getAllTopics();
            return ResponseEntity.ok(topicList);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<?> createTopic(@RequestParam String name) {
        try {
            topicService.createTopic(name);
            return ResponseEntity.ok("Thêm Topic Thành Công!");
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{idTopic}")
    public ResponseEntity<?> updateTopic(@PathVariable int idTopic, @RequestParam String name) {
        try {
            topicService.updateTopic(idTopic, name);
            return ResponseEntity.ok("Sửa Topic Thành Công!");
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{idTopic}")
    public ResponseEntity<?> deleteTopic(@PathVariable int idTopic) {
        try {
            topicService.deleteTopic(idTopic);
            return ResponseEntity.ok().build();
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
