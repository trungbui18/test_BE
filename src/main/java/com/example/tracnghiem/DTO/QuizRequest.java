package com.example.tracnghiem.DTO;

import com.example.tracnghiem.Model.Topic;

import java.util.Date;

public class QuizRequest {
    private String title;
    private String description;
    private String topicName;
    private int time;

    public QuizRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
