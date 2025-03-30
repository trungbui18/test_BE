package com.example.tracnghiem.DTO;

public class SubmitRespone {
    private int score;
    private int totalQuestions;

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public SubmitRespone() {
    }
}
