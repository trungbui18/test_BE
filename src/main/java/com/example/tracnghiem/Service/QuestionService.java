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
    public QuestionUpsertDTO createQuestion(QuestionRequest questionRequest, MultipartFile image) {
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
        QuestionUpsertDTO questionUpsertDTO = new QuestionUpsertDTO();
        questionUpsertDTO.setId(question.getId());
        questionUpsertDTO.setQuestion(question.getQuestion());
        questionUpsertDTO.setImg(question.getImg());
        questionUpsertDTO.setAnswers(answers);
        return questionUpsertDTO;
    }
    @Transactional
    public QuestionUpsertDTO updateQuestion(QuestionUpsertDTO questionUpsertDTO, int idQuestion, MultipartFile image) {
        // 1. Validate ID
        if (questionUpsertDTO.getId() != idQuestion) {
            throw new RuntimeException("ID câu hỏi không khớp");
        }

        Question question = questionRepository.findById(idQuestion)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy câu hỏi"));
        question.setQuestion(questionUpsertDTO.getQuestion());

        if (image != null && !image.isEmpty()) {
            question.setImg(imageService.uploadImage(image));
        }
        Question savedQuestion = questionRepository.save(question);


        List<Answer> updatedAnswers = questionUpsertDTO.getAnswers();
        List<Answer> finalAnswers = new ArrayList<>();

        for (Answer updatedAnswer : updatedAnswers) {
            Answer existingAnswer = answerRepository.findById(updatedAnswer.getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy Answer ID: " + updatedAnswer.getId()));

            existingAnswer.setContent(updatedAnswer.getContent());
            existingAnswer.setCorrect(updatedAnswer.isCorrect());
            existingAnswer.setQuestion(savedQuestion);

            finalAnswers.add(existingAnswer);
        }

        List<Answer> savedAnswers = answerRepository.saveAll(finalAnswers);

        questionUpsertDTO.setId(savedQuestion.getId());
        questionUpsertDTO.setQuestion(savedQuestion.getQuestion());
        questionUpsertDTO.setImg(savedQuestion.getImg());
        questionUpsertDTO.setAnswers(savedAnswers);

        return questionUpsertDTO;
    }
}
