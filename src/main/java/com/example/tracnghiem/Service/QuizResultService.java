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
    public List<QuizResultDTO> getQuizResultsByIdUser(int idUser) {
        List<QuizResult> quizResults = quizResultRepository.findAllByUserId(idUser);
        List<QuizResultDTO> quizResultDTOs = new ArrayList<>();

        for (QuizResult quizResult : quizResults) {
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
            quizResultDTO.setSubmitted_at(quizResult.getSubmitted_at());

            if (quizResult.getUserResults() != null) {
                List<UserResultDTO> userResultDTOs = new ArrayList<>();
                for (UserResult userResult : quizResult.getUserResults()) {
                    UserResultDTO userResultDTO = new UserResultDTO();
                    userResultDTO.setId(userResult.getId());

                    if (userResult.getQuestion() != null) {
                        QuestionDTO questionDTO = new QuestionDTO();
                        questionDTO.setId(userResult.getQuestion().getId());
                        questionDTO.setQuestion(userResult.getQuestion().getQuestion());
                        questionDTO.setImg(userResult.getQuestion().getImg());

                        List<AnswerDTO> answerDTOs = new ArrayList<>();
                        List<Answer> answers = userResult.getQuestion().getAnswers();
                        for (Answer answer : answers) {
                            AnswerDTO answerDTO = new AnswerDTO();
                            answerDTO.setId(answer.getId());
                            answerDTO.setContent(answer.getContent());
                            answerDTOs.add(answerDTO);
                        }
                        questionDTO.setAnswers(answerDTOs);

                        userResultDTO.setQuestion(questionDTO);
                    }


                    if (userResult.getSelectedAnswer() != null) {
                        userResultDTO.setSelectedAnswerId(userResult.getSelectedAnswer().getId());
                        userResultDTO.setSelectedAnswerText(userResult.getSelectedAnswer().getContent());
                    }

                    userResultDTO.setCorrect(userResult.isIs_correct());
                    userResultDTOs.add(userResultDTO);
                }
                quizResultDTO.setUserResultDTOList(userResultDTOs);
            }

            quizResultDTOs.add(quizResultDTO);
        }
        return quizResultDTOs;
    }
    public List<QuizResultDTO> getQuizResultsByIdQuiz(int idQuiz) {
        List<QuizResult> quizResults = quizResultRepository.findAllByQuizId(idQuiz);
        List<QuizResultDTO> quizResultDTOs = new ArrayList<>();

        for (QuizResult quizResult : quizResults) {
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
            quizResultDTO.setSubmitted_at(quizResult.getSubmitted_at());

            if (quizResult.getUserResults() != null) {
                List<UserResultDTO> userResultDTOs = new ArrayList<>();
                for (UserResult userResult : quizResult.getUserResults()) {
                    UserResultDTO userResultDTO = new UserResultDTO();
                    userResultDTO.setId(userResult.getId());

                    if (userResult.getQuestion() != null) {
                        QuestionDTO questionDTO = new QuestionDTO();
                        questionDTO.setId(userResult.getQuestion().getId());
                        questionDTO.setQuestion(userResult.getQuestion().getQuestion());
                        questionDTO.setImg(userResult.getQuestion().getImg());

                        List<AnswerDTO> answerDTOs = new ArrayList<>();
                        List<Answer> answers = userResult.getQuestion().getAnswers();
                        for (Answer answer : answers) {
                            AnswerDTO answerDTO = new AnswerDTO();
                            answerDTO.setId(answer.getId());
                            answerDTO.setContent(answer.getContent());
                            answerDTOs.add(answerDTO);
                        }
                        questionDTO.setAnswers(answerDTOs);

                        userResultDTO.setQuestion(questionDTO);
                    }


                    if (userResult.getSelectedAnswer() != null) {
                        userResultDTO.setSelectedAnswerId(userResult.getSelectedAnswer().getId());
                        userResultDTO.setSelectedAnswerText(userResult.getSelectedAnswer().getContent());
                    }

                    userResultDTO.setCorrect(userResult.isIs_correct());
                    userResultDTOs.add(userResultDTO);
                }
                quizResultDTO.setUserResultDTOList(userResultDTOs);
            }

            quizResultDTOs.add(quizResultDTO);
        }
        return quizResultDTOs;
    }
}
