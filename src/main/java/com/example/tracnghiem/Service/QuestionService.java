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
    public QuestionCreateDTO updateQuestion(QuestionCreateDTO questionCreateDTO,int idQuestion, MultipartFile image) {
        if (questionCreateDTO.getId()!=idQuestion) {
            throw new RuntimeException("Không Trùng idQuestion");
        }
        Question question = questionRepository.findById(idQuestion).orElseThrow(()->new RuntimeException("Không Tìm Thấy Question!"));
        question.setQuestion(questionCreateDTO.getQuestion());
        if (image != null && !image.isEmpty()) {
            String imagePath = imageService.uploadImage(image);
            question.setImg(imagePath);
        }
        questionRepository.save(question);
        List<Answer> answers = answerRepository.findByQuestionId(question.getId());
        Map<Integer,Answer> answerMap = new HashMap<>();
        answerMap=answers.stream().collect(Collectors.toMap(Answer::getId, answer -> answer));
        List<Answer> answerList=questionCreateDTO.getAnswers();
        List<Answer> finalAnswers = new ArrayList<>();
        for (Answer updatedAnswer : answerList) {
            if (updatedAnswer.getId() >0 && answerMap.containsKey(updatedAnswer.getId())) {
                // Nếu ID có trong database, chỉ cập nhật nội dung và đúng/sai
                Answer existingAnswer = answerMap.get(updatedAnswer.getId());
                existingAnswer.setContent(updatedAnswer.getContent());
                existingAnswer.setCorrect(updatedAnswer.isCorrect());
                finalAnswers.add(existingAnswer);
            } else {
                // Nếu là câu trả lời mới, thêm mới
                Answer newAnswer = new Answer();
                newAnswer.setContent(updatedAnswer.getContent());
                newAnswer.setCorrect(updatedAnswer.isCorrect());
                newAnswer.setQuestion(question);
                finalAnswers.add(newAnswer);
            }
        }
        answerRepository.saveAll(finalAnswers);
        QuestionCreateDTO questionCreate = new QuestionCreateDTO();
        questionCreateDTO.setId(question.getId());
        questionCreateDTO.setQuestion(question.getQuestion());
        questionCreateDTO.setImg(question.getImg());
        questionCreateDTO.setAnswers(finalAnswers);
        return questionCreate;
    }
}
