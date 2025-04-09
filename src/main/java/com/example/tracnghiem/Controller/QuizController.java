package com.example.tracnghiem.Controller;

import com.example.tracnghiem.DTO.*;
import com.example.tracnghiem.Model.Quiz;
import com.example.tracnghiem.Model.QuizResult;
import com.example.tracnghiem.Service.QuizService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }
    @PostMapping(value ="/create" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createQuiz(@RequestParam String title,
                                        @RequestParam String description,
                                        @RequestParam String topicName ,
                                        @RequestParam int time,
                                        @RequestParam (required = false) MultipartFile image,
                                        @RequestParam int idUser ) {
        try {
            int id=quizService.createQuiz(title,description,topicName,time,idUser,image);
            return ResponseEntity.ok(id);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/topic/{idTopic}")

    public ResponseEntity<?> getAllQuizByTopicId(@PathVariable int idTopic) {
        try {
            List<QuizDTO> quizDTOS=quizService.getQuizListByIdTopic(idTopic);
            return ResponseEntity.ok(quizDTOS);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{idQuiz}")
    public ResponseEntity<?> getQuiz(@PathVariable int idQuiz) {
        try {
            QuizDTO quizDTO=quizService.getQuizById(idQuiz);
            return ResponseEntity.ok(quizDTO);
        }catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("find/{code}")
    public ResponseEntity<?> findQuiz(@PathVariable String code) {
        try {
            int idQuiz=quizService.findQuizByCode(code);
            return ResponseEntity.ok(idQuiz);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("delete/{idQuiz}")
    public ResponseEntity<?> deleteQuiz(@PathVariable int idQuiz) {
        try {
            quizService.deteleQuiz(idQuiz);
            return ResponseEntity.ok().body("Xóa Quiz Thành Công!");
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping(value="update/{idQuiz}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateQuiz(
            @PathVariable int idQuiz,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String topicName,
            @RequestParam int time,
            @RequestParam int idUser,
            @RequestParam (required = false) MultipartFile image) {
        try {
            quizService.updateQuiz(title,description,topicName,time, idQuiz, idUser, image);
            return ResponseEntity.ok("Sửa Quiz Thành Công!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("submit")
    public ResponseEntity<?> submitQuiz(@RequestBody SubmitRequest submitRequest) {
        try {
            SubmitRespone submitRespone=quizService.submitQuiz(submitRequest);
            return ResponseEntity.ok(submitRespone);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("user")
    public ResponseEntity<?> getQuizByUser(@RequestParam int idUser) {
        try {
            List<QuizDTO> quizDTOList=quizService.getQuizListUser(idUser);
            return ResponseEntity.ok(quizDTOList);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
