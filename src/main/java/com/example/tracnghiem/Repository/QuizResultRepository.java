package com.example.tracnghiem.Repository;

import com.example.tracnghiem.Model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Integer> {
    List<QuizResult> findAllByUserId(int userId);
    List<QuizResult> findAllByQuizId(int quizId);
}
