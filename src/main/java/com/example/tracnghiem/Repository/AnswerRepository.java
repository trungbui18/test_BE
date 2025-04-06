package com.example.tracnghiem.Repository;

import com.example.tracnghiem.Model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByQuestionId(int questionId);
    Optional<Answer> findById(int answerId);
    List<Answer> findByQuestionIdInAndCorrect(List<Integer> questionIds, boolean b);
}
