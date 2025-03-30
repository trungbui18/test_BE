package com.example.tracnghiem.Repository;

import com.example.tracnghiem.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findAllByQuiz_Id(int quizId);
    Optional<Question> findById(int questionId);
}
