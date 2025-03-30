package com.example.tracnghiem.DTO;

import java.util.Map;

public class SubmitRequest {
    private int idQuiz;
    private Map<Integer,Integer> answers;

    public SubmitRequest() {
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public Map<Integer, Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Integer, Integer> answers) {
        this.answers = answers;
    }
}
