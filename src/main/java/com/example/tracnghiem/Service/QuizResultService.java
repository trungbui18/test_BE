package com.example.tracnghiem.Service;

import com.example.tracnghiem.DTO.*;
import com.example.tracnghiem.Model.Answer;
import com.example.tracnghiem.Model.QuizResult;
import com.example.tracnghiem.Model.UserResult;
import com.example.tracnghiem.Repository.QuizResultRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizResultService {
    private final QuizResultRepository quizResultRepository;
    public QuizResultService(QuizResultRepository quizResultRepository) {
        this.quizResultRepository = quizResultRepository;
    }
    public List<UserQuizResultDTO> getQuizResultsByIdQuiz(int idQuiz) {
        List<QuizResult> quizResults = quizResultRepository.findAllByQuizId(idQuiz);
        List<UserQuizResultDTO> userQuizResultDTOs=new ArrayList<>();
        for(QuizResult quizResult:quizResults){
            UserQuizResultDTO userQuizResultDTO=new UserQuizResultDTO();
            userQuizResultDTO.setIdQuizResult(quizResult.getId());
            userQuizResultDTO.setSubmittedAt(quizResult.getSubmitted_at());
            userQuizResultDTO.setScore(quizResult.getScore());
            UserDTO userDTO=new UserDTO();
            userDTO.setId(quizResult.getUser().getId());
            userDTO.setUsername(quizResult.getUser().getUsername());
            userDTO.setEmail(quizResult.getUser().getEmail());
            userQuizResultDTO.setUserDTO(userDTO);
            userQuizResultDTOs.add(userQuizResultDTO);
        }
        return userQuizResultDTOs;
    }
    public List<QuizResultSimpleDTO> getQuizResultsByIdUser(int idUser){
        List<QuizResult> quizResults=quizResultRepository.findAllByUserId(idUser);
        List<QuizResultSimpleDTO> quizResultSimpleDTOs = new ArrayList<>();
        for (QuizResult quizResult : quizResults) {
            QuizResultSimpleDTO quizResultSimpleDTO = new QuizResultSimpleDTO();
            quizResultSimpleDTO.setId(quizResult.getId());
            quizResultSimpleDTO.setSubmittedAt(quizResult.getSubmitted_at());
            quizResultSimpleDTO.setScore(quizResult.getScore());
            if (quizResult.getQuiz()!=null){
                QuizDTO quizDTO = new QuizDTO();
                quizDTO.setId(quizResult.getQuiz().getId());
                quizDTO.setTitle(quizResult.getQuiz().getTitle());
                quizDTO.setDescription(quizResult.getQuiz().getDescription());
                quizDTO.setImage(quizResult.getQuiz().getImage());
                quizDTO.setTopic(quizResult.getQuiz().getTopic());
                quizDTO.setCreated(quizResult.getQuiz().getCreated());
                quizDTO.setTime(quizResult.getQuiz().getTime());
                quizDTO.setCode(quizResult.getQuiz().getCode());
                quizDTO.setTotalQuestions(quizResult.getTotalQuestions());
                quizResultSimpleDTO.setQuiz(quizDTO);
            }
            quizResultSimpleDTOs.add(quizResultSimpleDTO);
        }
        return quizResultSimpleDTOs;
    }
    public QuizResultDTO getQuizResultDetail(int resultId) {
        QuizResult quizResult = quizResultRepository.findById(resultId).orElseThrow(()->new RuntimeException("Không Tìm Thấy Kết Quả Quiz"));
        QuizResultDTO quizResultDTO = new QuizResultDTO();
        quizResultDTO.setId(quizResult.getId());

        if (quizResult.getUser() != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(quizResult.getUser().getId());
            userDTO.setUsername(quizResult.getUser().getUsername());
            userDTO.setEmail(quizResult.getUser().getEmail());
            quizResultDTO.setUser(userDTO);
        }

        if (quizResult.getQuiz() != null) {
            quizResultDTO.setIdQuiz(quizResult.getQuiz().getId());
            quizResultDTO.setQuizTitle(quizResult.getQuiz().getTitle());
        }

        quizResultDTO.setScore(quizResult.getScore());
        quizResultDTO.setTotalQuestions(quizResult.getTotalQuestions());
        quizResultDTO.setSubmittedAt(quizResult.getSubmitted_at());


        if (quizResult.getUserResults() != null) {
            List<UserResultDTO> userResultDTOs = new ArrayList<>();
            for (UserResult userResult : quizResult.getUserResults()) {
                UserResultDTO userResultDTO = new UserResultDTO();
                userResultDTO.setId(userResult.getId());


                if (userResult.getQuestion() != null) {
                    QuestionDTO questionDTO = new QuestionDTO();
                    questionDTO.setId(userResult.getQuestion().getId());
                    questionDTO.setContent(userResult.getQuestion().getQuestion());
                    questionDTO.setImg(userResult.getQuestion().getImg());
                    questionDTO.setAnswers(userResult.getQuestion().getAnswers());
                    userResultDTO.setQuestion(questionDTO);
                }

                if (userResult.getSelectedAnswer() != null) {
                    userResultDTO.setSelectedAnswerId(userResult.getSelectedAnswer().getId());
                    userResultDTO.setSelectedAnswerText(userResult.getSelectedAnswer().getContent());
                }

                userResultDTO.setCorrect(userResult.isIs_correct());
                userResultDTOs.add(userResultDTO);
            }
            quizResultDTO.setUserResults(userResultDTOs);
        }

        return quizResultDTO;
    }

}
