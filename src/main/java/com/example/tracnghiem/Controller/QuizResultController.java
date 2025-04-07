package com.example.tracnghiem.Controller;

import com.example.tracnghiem.DTO.QuizResultDTO;
import com.example.tracnghiem.Service.QuizResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/result")
public class QuizResultController {
    private final QuizResultService quizResultService;

    public QuizResultController(QuizResultService quizResultService) {
        this.quizResultService = quizResultService;
    }
    @GetMapping("/user/{idUser}")
    public ResponseEntity<?> getQuizResultForUser(@PathVariable int idUser) {
        try {
            List<QuizResultDTO> quizResultDTOS=quizResultService.getQuizResultsByIdUser(idUser);
            return ResponseEntity.ok(quizResultDTOS);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/quiz/{idQuiz}")
    public ResponseEntity<?> getQuizResultForQuiz(@PathVariable int idQuiz) {
        try {
            List<QuizResultDTO> quizResultDTOS=quizResultService.getQuizResultsByIdQuiz(idQuiz);
            return ResponseEntity.ok(quizResultDTOS);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
