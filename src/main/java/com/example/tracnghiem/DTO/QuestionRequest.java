package com.example.tracnghiem.DTO;

import java.util.List;

public class QuestionRequest {
    private String question;
    private int idQuiz;
    private List<AnswerRequest> answers;
    public QuestionRequest() {}

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AnswerRequest> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerRequest> answers) {
        this.answers = answers;
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }
}
