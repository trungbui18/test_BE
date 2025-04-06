package com.example.tracnghiem.Service;

import com.example.tracnghiem.DTO.*;
import com.example.tracnghiem.Model.Answer;
import com.example.tracnghiem.Model.Question;
import com.example.tracnghiem.Model.Quiz;
import com.example.tracnghiem.Repository.AnswerRepository;
import com.example.tracnghiem.Repository.QuestionRepository;
import com.example.tracnghiem.Repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final QuizRepository quizRepository;
    private final ImageService imageService;
    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository, QuizRepository quizRepository, ImageService imageService) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.quizRepository = quizRepository;
        this.imageService = imageService;
    }
    public List<QuestionDTO> getAllQuestions(int idQuiz) {
        List<Question> questions = questionRepository.findAllByQuiz_Id(idQuiz);
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setId(question.getId());
            questionDTO.setQuestion(question.getQuestion());
            questionDTO.setImg(question.getImg());
            List<AnswerDTO> answerDTOs = new ArrayList<>();
            for (Answer answer : question.getAnswers()) {
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setId(answer.getId());
                answerDTO.setContent(answer.getContent());
                answerDTOs.add(answerDTO);
            }
            questionDTO.setAnswers(answerDTOs);
            questionDTOs.add(questionDTO);
        }
        return questionDTOs;
    }
    public void DeleteQuestion(int idQuestion) {
        questionRepository.deleteById(idQuestion);
    }
    @Transactional
    public QuestionCreateDTO createQuestion(QuestionRequest questionRequest, MultipartFile image) {
        Question question = new Question();
        question.setQuestion(questionRequest.getQuestion());
        if (image != null && !image.isEmpty()) {
            String imagePath = imageService.uploadImage(image);
            question.setImg(imagePath);
        }
        Quiz quiz = quizRepository.findById(questionRequest.getIdQuiz()).orElseThrow(()-> new RuntimeException("Không Tìm Thấy Quiz"));
        question.setQuiz(quiz);
        question= questionRepository.save(question);
        List<Answer> answers = new ArrayList<>();
        int countIsCorrect = 0;
        for (AnswerRequest answerRequest : questionRequest.getAnswers()) {
            Answer answer1 = new Answer();
            answer1.setContent(answerRequest.getContent());
            answer1.setCorrect(answerRequest.isCorrect());
            if (answerRequest.isCorrect()) {
                countIsCorrect++;
            }
            answer1.setQuestion(question);
            answers.add(answer1);
        }
        if (countIsCorrect>1){
            throw new RuntimeException("Không thể có hơn 1 câu đúng!");
        }
        answerRepository.saveAll(answers);
        QuestionCreateDTO questionCreateDTO = new QuestionCreateDTO();
        questionCreateDTO.setId(question.getId());
        questionCreateDTO.setQuestion(question.getQuestion());
        questionCreateDTO.setImg(question.getImg());
        questionCreateDTO.setAnswers(answers);
        return questionCreateDTO;
    }
    @Transactional
    public QuestionCreateDTO updateQuestion(QuestionCreateDTO questionCreateDTO, int idQuestion, MultipartFile image) {
        // 1. Validate ID
        if (questionCreateDTO.getId() != idQuestion) {
            throw new RuntimeException("ID câu hỏi không khớp");
        }

        // 2. Cập nhật câu hỏi chính
        Question question = questionRepository.findById(idQuestion)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy câu hỏi"));
        question.setQuestion(questionCreateDTO.getQuestion());

        if (image != null && !image.isEmpty()) {
            question.setImg(imageService.uploadImage(image));
        }
        Question savedQuestion = questionRepository.save(question);

        // 3. Cập nhật từng câu trả lời
        List<Answer> updatedAnswers = questionCreateDTO.getAnswers();
        List<Answer> finalAnswers = new ArrayList<>();

        for (Answer updatedAnswer : updatedAnswers) {
            Answer existingAnswer = answerRepository.findById(updatedAnswer.getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy Answer ID: " + updatedAnswer.getId()));

            // Chỉ cập nhật các trường được phép thay đổi
            existingAnswer.setContent(updatedAnswer.getContent()); // Thêm dòng này nếu muốn cập nhật content
            existingAnswer.setCorrect(updatedAnswer.isCorrect());
            existingAnswer.setQuestion(savedQuestion); // Liên kết với câu hỏi đã lưu

            finalAnswers.add(existingAnswer);
        }

        // 4. Lưu đồng loạt
        List<Answer> savedAnswers = answerRepository.saveAll(finalAnswers);

        // 5. Cập nhật kết quả trả về
        questionCreateDTO.setId(savedQuestion.getId());
        questionCreateDTO.setQuestion(savedQuestion.getQuestion());
        questionCreateDTO.setImg(savedQuestion.getImg());
        questionCreateDTO.setAnswers(savedAnswers);

        return questionCreateDTO;
    }
}
