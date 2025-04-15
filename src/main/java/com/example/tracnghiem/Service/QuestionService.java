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
import java.util.List;

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
            questionDTO.setContent(question.getQuestion());
            questionDTO.setImg(question.getImg());
            questionDTO.setAnswers(question.getAnswers());
            questionDTOs.add(questionDTO);
        }
        return questionDTOs;
    }
    public void DeleteQuestion(int idQuestion) {
        questionRepository.deleteById(idQuestion);
    }
    @Transactional
    public void createQuestion(QuestionRequest questionRequest, MultipartFile image) {
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
    }
    @Transactional
    public void updateQuestion(QuestionUpsertDTO questionUpsertDTO, int idQuestion, MultipartFile image) {
        Question question = questionRepository.findById(idQuestion)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy câu hỏi"));
        question.setQuestion(questionUpsertDTO.getContent());

        if (image != null && !image.isEmpty()) {
            question.setImg(imageService.uploadImage(image));
        }
        Question savedQuestion = questionRepository.save(question);


        List<Answer> updatedAnswers = questionUpsertDTO.getAnswers();
        List<Answer> finalAnswers = new ArrayList<>();

        for (Answer updatedAnswer : updatedAnswers) {
            Answer existingAnswer = answerRepository.findById(updatedAnswer.getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy Answer ID: " + updatedAnswer.getId()));
            if (existingAnswer.getQuestion().getId()!=idQuestion){
                throw new RuntimeException("Id câu trả lời không thuộc câu hỏi!");
            }
            existingAnswer.setContent(updatedAnswer.getContent());
            existingAnswer.setCorrect(updatedAnswer.isCorrect());
            existingAnswer.setQuestion(savedQuestion);
            finalAnswers.add(existingAnswer);
        }
        answerRepository.saveAll(finalAnswers);
    }

    public QuestionDTO getQuestionById(int idQuestion) {
        Question question = questionRepository.findById(idQuestion).orElseThrow(()->new RuntimeException("deo thay"));
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setContent(question.getQuestion());
        questionDTO.setImg(question.getImg());
        questionDTO.setAnswers(question.getAnswers());
        return questionDTO;
    }
}
