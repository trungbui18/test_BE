package com.example.tracnghiem.Controller;

import com.example.tracnghiem.DTO.QuestionCreateDTO;
import com.example.tracnghiem.DTO.QuestionDTO;
import com.example.tracnghiem.DTO.QuestionRequest;
import com.example.tracnghiem.Service.QuestionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@CrossOrigin("*")
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
    @DeleteMapping("/{idQuestion}")
    public ResponseEntity<?> deleteQuestionById(@PathVariable int idQuestion) {
        try {
            questionService.DeleteQuestion(idQuestion);
            return ResponseEntity.ok().body("Xóa Question Thành Công");
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addQuestion(@RequestParam String questionRequest, @RequestParam(required = false) MultipartFile imgage) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            QuestionRequest request=mapper.readValue(questionRequest,QuestionRequest.class);
            QuestionCreateDTO questionCreateDTO= questionService.createQuestion(request,imgage);
            return ResponseEntity.ok(questionCreateDTO);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateQuestion(@RequestParam String questionCreateDTO,@RequestParam int idQuestion, @RequestParam(required = false) MultipartFile imgage) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            QuestionCreateDTO request=mapper.readValue(questionCreateDTO,QuestionCreateDTO.class);
            QuestionCreateDTO response = questionService.updateQuestion(request, idQuestion, imgage);
            return ResponseEntity.ok(response);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
