package com.example.tracnghiem.DTO;

public class UserResultDTO {
    private int id;
    private QuestionDTO question;
    private int selectedAnswerId;
    private String selectedAnswerText;
    private boolean isCorrect;

    public UserResultDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getSelectedAnswerId() {
        return selectedAnswerId;
    }

    public void setSelectedAnswerId(int selectedAnswerId) {
        this.selectedAnswerId = selectedAnswerId;
    }

    public String getSelectedAnswerText() {
        return selectedAnswerText;
    }

    public void setSelectedAnswerText(String selectedAnswerText) {
        this.selectedAnswerText = selectedAnswerText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public QuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDTO question) {
        this.question = question;
    }
}
