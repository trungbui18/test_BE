package com.example.tracnghiem.Service;

import com.example.tracnghiem.Model.Topic;
import com.example.tracnghiem.Repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
    public void createTopic(String name) {
        if (topicRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Topic Đã Tồn Tại!");
        }
        Topic topic = new Topic();
        topic.setName(name);
        topicRepository.save(topic);
    }
    public void deleteTopic(int id) {
        topicRepository.deleteById(id);
    }
    public void updateTopic(int id, String name) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Không Tìm Thấy Topic!"));
        topic.setName(name);
        topicRepository.save(topic);
    }
}
