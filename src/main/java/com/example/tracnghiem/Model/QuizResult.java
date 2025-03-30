package com.example.tracnghiem.Model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "quiz_result")
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_quiz")
    private Quiz quiz;
    private int score;
    @Column(name = "total_question")
    private int totalQuestions;
    private Date submitted_at;
    @OneToMany(mappedBy = "quizResult")
    private List<UserResult> userResults;

    public QuizResult() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Date getSubmitted_at() {
        return submitted_at;
    }

    public void setSubmitted_at(Date submitted_at) {
        this.submitted_at = submitted_at;
    }

    public List<UserResult> getUserResults() {
        return userResults;
    }

    public void setUserResults(List<UserResult> userResults) {
        this.userResults = userResults;
    }
}
