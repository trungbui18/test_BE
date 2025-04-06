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
    @GetMapping("/{idUser}")
    public ResponseEntity<?> getQuizResult(@PathVariable int idUser) {
        try {
            List<QuizResultDTO> quizResultDTOS=quizResultService.getQuizResultsByIdUser(idUser);
            return ResponseEntity.ok(quizResultDTOS);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
