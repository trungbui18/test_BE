package com.example.tracnghiem.Repository;

import com.example.tracnghiem.Model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByQuestionId(int questionId);

    List<Answer> findByQuestionIdInAndCorrect(List<Integer> questionIds, boolean b);
}
