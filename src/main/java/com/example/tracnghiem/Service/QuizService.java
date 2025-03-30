package com.example.tracnghiem.Service;

import com.example.tracnghiem.DTO.*;
import com.example.tracnghiem.Model.*;
import com.example.tracnghiem.Repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final ImageService imageService;
    private final UserResultRepository userResultRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final QuizResultRepository quizResultRepository;

    public QuizService(QuizRepository quizRepository, UserRepository userRepository, TopicRepository topicRepository, ImageService imageService, UserResultRepository userResultRepository, QuestionRepository questionRepository, AnswerRepository answerRepository, QuizResultRepository quizResultRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.imageService = imageService;
        this.userResultRepository = userResultRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.quizResultRepository = quizResultRepository;
    }
    public QuizDTO createQuiz(QuizRequest quizRequest, int idUser, MultipartFile image) {
        User user=userRepository.findById(idUser).orElseThrow(()-> new RuntimeException("Không Tìm Thấy User"));
        Topic topic= topicRepository.findByName(quizRequest.getTopicName()).orElseThrow(()->new RuntimeException("Không Tìm Thấy Topic"));
        Quiz quiz = new Quiz();
        quiz.setTitle(quizRequest.getTitle());
        quiz.setDescription(quizRequest.getDescription());
        if (image != null && !image.isEmpty()) {
            String imagePath = imageService.uploadImage(image);
            quiz.setImage(imagePath);
        }
        quiz.setUser(user);
        quiz.setTopic(topic);
        String code;
        Random random = new Random();
        do {
            code = String.format("%06d", random.nextInt(1000000)); // Sinh code 6 chữ số
        } while (quizRepository.existsByCode(code));
        quiz.setCode(code);
        quiz.setCreated(new Date());
        quiz.setTime(quizRequest.getTime());
        quizRepository.save(quiz);
        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setId(quiz.getId());
        quizDTO.setTitle(quizRequest.getTitle());
        quizDTO.setDescription(quizRequest.getDescription());
        quizDTO.setCode(code);
        quizDTO.setCreated(quiz.getCreated());
        quizDTO.setTime(quiz.getTime());
        quizDTO.setTopic(quiz.getTopic());
        quizDTO.setImage(quiz.getImage());
        UserDTO userDTO = new UserDTO(user.getId(),user.getUsername(),user.getEmail());
        quizDTO.setUser(userDTO);
        return quizDTO;
    }

    public QuizDTO getQuizByCode(String code) {
        Quiz quiz=quizRepository.findByCode(code).orElseThrow(()->new RuntimeException("Không Tìm Thấy Bài Thi"));
        QuizDTO quizDTO=new QuizDTO();
        quizDTO.setId(quiz.getId());
        quizDTO.setTitle(quiz.getTitle());
        quizDTO.setDescription(quiz.getDescription());
        quizDTO.setCode(code);
        quizDTO.setCreated(quiz.getCreated());
        quizDTO.setTime(quiz.getTime());
        quizDTO.setTopic(quiz.getTopic());
        quizDTO.setImage(quiz.getImage());
        UserDTO userDTO= new UserDTO(quiz.getUser().getId(),quiz.getUser().getUsername(),quiz.getUser().getEmail());
        quizDTO.setUser(userDTO);
        return quizDTO;
    }
    public void deteleQuiz(int idQuiz) {
        quizRepository.deleteById(idQuiz);
    }
    public QuizDTO updateQuiz(QuizRequest quizRequest, int idQuiz, int idUser, MultipartFile image) {
        Quiz quiz=quizRepository.findById(idQuiz).orElseThrow(()->new RuntimeException("Không Tìm Thấy Quiz"));
        if(idUser!=quiz.getUser().getId()){
            throw new RuntimeException("Không Đúng Người Tạo Quiz");
        }
        quiz.setTitle(quizRequest.getTitle());
        quiz.setDescription(quizRequest.getDescription());
        Topic topic=topicRepository.findByName(quizRequest.getTopicName()).orElseThrow(()->new RuntimeException("Không Tìm Thấy Topic!"));
        quiz.setTopic(topic);
        if (image != null && !image.isEmpty()) {
            String imagePath = imageService.uploadImage(image);
            quiz.setImage(imagePath);
        }
        quiz.setTime(quizRequest.getTime());
        quizRepository.save(quiz);
        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setId(quiz.getId());
        quizDTO.setTitle(quizRequest.getTitle());
        quizDTO.setDescription(quizRequest.getDescription());
        quizDTO.setCode(quiz.getCode());
        quizDTO.setCreated(quiz.getCreated());
        quizDTO.setTime(quiz.getTime());
        quizDTO.setTopic(quiz.getTopic());
        quizDTO.setImage(quiz.getImage());
        UserDTO userDTO=new UserDTO(quiz.getUser().getId(),quiz.getUser().getUsername(),quiz.getUser().getEmail());
        quizDTO.setUser(userDTO);
        return quizDTO;
    }
    @Transactional
    public SubmitRespone submitQuiz(SubmitRequest submitRequest, int idUser) {
        Quiz quiz= quizRepository.findById(submitRequest.getIdQuiz()).orElseThrow(()->new RuntimeException("Không Tìm Thấy Quiz"));
        User user=userRepository.findById(idUser).orElseThrow(()->new RuntimeException("Không Tìm Thấy User!"));
        List<Integer> questionIds= new ArrayList<>(submitRequest.getAnswers().keySet());
        List<Question> questions=questionRepository.findAllById(questionIds);
        List<Answer> correctAnswers=answerRepository.findByQuestionIdInAndCorrect(questionIds,true);
        List<Answer> selectedAnswers=answerRepository.findAllById(submitRequest.getAnswers().values());
        Map<Integer,Question> questionMap=questions.stream().collect(Collectors.toMap(Question::getId, q -> q));
        Map<Integer, Answer> correctAnswerMap=correctAnswers.stream().collect(Collectors.toMap(a-> a.getQuestion().getId(), a -> a));
        Map<Integer, Answer> selectedAnswerMap=selectedAnswers.stream().collect(Collectors.toMap(Answer::getId, a -> a));
        int score=0;
        int totalQuestion=quiz.getQuestions().size();
        List<UserResult> userResults = new ArrayList<>();
        for (Map.Entry<Integer,Integer> entry: submitRequest.getAnswers().entrySet()) {
            int questionId=entry.getKey();
            int selectedAnswerId=entry.getValue();
            Question question=questionMap.get(questionId);
            if (question==null) {
                throw new RuntimeException("Không Tìm Thấy QuestionID "+questionId);
            }
            Answer selectedAnswer=selectedAnswerMap.get(selectedAnswerId);
            if (selectedAnswer==null) {
                throw new RuntimeException("Không Tìm Thầy AnswerID "+selectedAnswerId);
            }

            boolean isCorrect=false;
            if (selectedAnswer.getQuestion().getId()==questionId) {
                Answer correctAnswer=correctAnswerMap.get(questionId);
                if (correctAnswer != null && correctAnswer.getId() == selectedAnswerId) {
                    isCorrect = true;
                    score++;
                }
            }
            else {
                throw new RuntimeException("Câu Trả Lời Không Thuộc Câu Hỏi Này!");
            }
            UserResult userResult= new UserResult();
            userResult.setUser(user);
            userResult.setQuestion(question);
            userResult.setQuiz(quiz);
            userResult.setIs_correct(isCorrect);
            userResult.setSelectedAnswer(selectedAnswer);
            userResults.add(userResult);
        }
        QuizResult quizResult=new QuizResult();
        quizResult.setQuiz(quiz);
        quizResult.setScore(score);
        quizResult.setUser(user);
        quizResult.setTotalQuestions(totalQuestion);
        quizResult.setSubmitted_at(new Date());
        quizResultRepository.save(quizResult);
        userResults.forEach(userResult -> userResult.setQuizResult(quizResult));
        userResultRepository.saveAll(userResults);
        SubmitRespone submitRespone=new SubmitRespone();
        submitRespone.setScore(score);
        submitRespone.setTotalQuestions(totalQuestion);
        return submitRespone;
    }
}
