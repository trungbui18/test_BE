package com.example.tracnghiem.Controller;

import com.example.tracnghiem.DTO.QuestionUpsertDTO;
import com.example.tracnghiem.DTO.QuestionDTO;
import com.example.tracnghiem.DTO.QuestionRequest;
import com.example.tracnghiem.Service.QuestionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
    @GetMapping("/id-quiz/{idQuiz}")
    public ResponseEntity<?> getQuestionById(@PathVariable int idQuiz) {
        try {
            List<QuestionDTO> questionDTOS=questionService.getAllQuestions(idQuiz);
            return ResponseEntity.ok(questionDTOS);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("delete/{idQuestion}")
    public ResponseEntity<?> deleteQuestionById(@PathVariable int idQuestion) {
        try {
            questionService.DeleteQuestion(idQuestion);
            return ResponseEntity.ok().body("Xóa Question Thành Công");
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping(value = "create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addQuestion(
            @ModelAttribute QuestionRequest questionRequest,
            @RequestPart(required = false) MultipartFile image) {

        try {
            questionService.createQuestion(questionRequest, image);
            return ResponseEntity.ok("Thêm Câu Hỏi Thành Công!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping(value = "update/{idQuestion}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateQuestion(@ModelAttribute QuestionUpsertDTO questionUpsertDTO, @PathVariable int idQuestion, @RequestParam(required = false) MultipartFile image) {
        try {
            questionService.updateQuestion(questionUpsertDTO, idQuestion, image);
            return ResponseEntity.ok("Sửa Câu Hỏi Thành Công!");
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{idQuestion}")
    public ResponseEntity<?> getQuestion(@PathVariable int idQuestion) {
        try {
            QuestionDTO questionDTO = questionService.getQuestionById(idQuestion);
            return ResponseEntity.ok(questionDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Deo thay");
        }
    }
}
