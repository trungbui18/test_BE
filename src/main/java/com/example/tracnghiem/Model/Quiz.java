package com.example.tracnghiem.Model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private String image;
    @ManyToOne
    @JoinColumn(name = "id_topic")
    private Topic topic;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    private Date created;
    private int time;
    @Column(name = "code", unique = true, length = 6)
    private String code;
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Question> questions;
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<QuizResult> quizResults;
    public Quiz() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<QuizResult> getQuizResults() {
        return quizResults;
    }

    public void setQuizResults(List<QuizResult> quizResults) {
        this.quizResults = quizResults;
    }
}
