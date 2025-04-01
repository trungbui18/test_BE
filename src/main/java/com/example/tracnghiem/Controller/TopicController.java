package com.example.tracnghiem.Controller;

import com.example.tracnghiem.Model.Topic;
import com.example.tracnghiem.Service.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/topic")
public class TopicController {
    private final TopicService topicService;
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }
    @GetMapping
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
            Topic topic = topicService.createTopic(name);
            return ResponseEntity.ok(topic);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{idTopic}")
    public ResponseEntity<?> updateTopic(@PathVariable int idTopic, @RequestParam String name) {
        try {
            Topic topic = topicService.updateTopic(idTopic, name);
            return ResponseEntity.ok(topic);
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
