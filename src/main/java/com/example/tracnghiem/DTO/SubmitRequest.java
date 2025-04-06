package com.example.tracnghiem.DTO;

import java.util.List;
import java.util.Map;

public class SubmitRequest {
    private int idUser;
    private int idQuiz;
    private List<AnswerSelected> answers;

    public SubmitRequest() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public List<AnswerSelected> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerSelected> answers) {
        this.answers = answers;
    }
}
