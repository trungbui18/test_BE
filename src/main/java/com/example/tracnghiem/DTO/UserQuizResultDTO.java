package com.example.tracnghiem.DTO;

import java.util.Date;

public class UserQuizResultDTO {
    private int idQuizResult;
    private UserDTO userDTO;
    private int score;
    private Date submittedAt;
    public UserQuizResultDTO() {}

    public int getIdQuizResult() {
        return idQuizResult;
    }

    public void setIdQuizResult(int idQuizResult) {
        this.idQuizResult = idQuizResult;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Date submittedAt) {
        this.submittedAt = submittedAt;
    }
}
