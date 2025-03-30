package com.example.tracnghiem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private boolean correct;
    @ManyToOne
    @JoinColumn(name = "id_question")
    @JsonIgnore
    private Question question;

    @OneToMany(mappedBy = "selectedAnswer")
    @JsonIgnore
    private List<UserResult> userResults;
    public Answer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<UserResult> getUserResults() {
        return userResults;
    }

    public void setUserResults(List<UserResult> userResults) {
        this.userResults = userResults;
    }
}
