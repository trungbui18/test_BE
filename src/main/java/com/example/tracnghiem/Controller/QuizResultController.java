package com.example.tracnghiem.Controller;

import com.example.tracnghiem.DTO.QuizResultDTO;
import com.example.tracnghiem.DTO.QuizResultSimpleDTO;
import com.example.tracnghiem.DTO.UserQuizResultDTO;
import com.example.tracnghiem.Service.QuizResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
            List<QuizResultSimpleDTO> quizResultSimpleDTOs=quizResultService.getQuizResultsByIdUser(idUser);
            return ResponseEntity.ok(quizResultSimpleDTOs);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/quiz/{idQuiz}")
    public ResponseEntity<?> getQuizResultForQuiz(@PathVariable int idQuiz) {
        try {
            List<UserQuizResultDTO> userQuizResultDTOs=quizResultService.getQuizResultsByIdQuiz(idQuiz);
            return ResponseEntity.ok(userQuizResultDTOs);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/detail/{idQuizResult}")
    public ResponseEntity<?> getQuizResultForDetail(@PathVariable int idQuizResult) {
        try {
            QuizResultDTO quizResultDTO=quizResultService.getQuizResultDetail(idQuizResult);
            return ResponseEntity.ok(quizResultDTO);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
