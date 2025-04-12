package com.example.tracnghiem.Service;

import com.example.tracnghiem.DTO.*;
import com.example.tracnghiem.Model.*;
import com.example.tracnghiem.Repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
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

    public List<QuizDTO> getAllQuizzes(){
        List<Quiz> quizzes = quizRepository.findAll();
        List<QuizDTO> listQuizzDTO = new ArrayList<>();
        for (Quiz quiz : quizzes) {
            QuizDTO quizDTO=new QuizDTO();
            quizDTO.setId(quiz.getId());
            quizDTO.setTitle(quiz.getTitle());
            quizDTO.setDescription(quiz.getDescription());
            quizDTO.setCode(quiz.getCode());
            quizDTO.setCreated(quiz.getCreated());
            quizDTO.setTime(quiz.getTime());
            quizDTO.setTopic(quiz.getTopic());
            quizDTO.setImage(quiz.getImage());
            UserDTO userDTO= new UserDTO(quiz.getUser().getId(),quiz.getUser().getUsername(),quiz.getUser().getEmail());
            quizDTO.setUser(userDTO);
            int totalQuestion= quiz.getQuestions().size();
            quizDTO.setTotalQuestions(totalQuestion);
            listQuizzDTO.add(quizDTO);
        }
        return listQuizzDTO;
    }

    public int createQuiz(String title, String description, String topicName,int time, int idUser, MultipartFile image) {
        User user=userRepository.findById(idUser).orElseThrow(()-> new RuntimeException("Không Tìm Thấy User"));
        Topic topic= topicRepository.findByName(topicName).orElseThrow(()->new RuntimeException("Không Tìm Thấy Topic"));
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setDescription(description);
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
        quiz.setTime(time);
        quizRepository.save(quiz);
        return quiz.getId();
    }
    public QuizDTO getQuizById(int id) {
        Quiz quiz=quizRepository.findById(id).orElseThrow(()->new RuntimeException("Không Tìm Thấy Bài Thi"));
        QuizDTO quizDTO=new QuizDTO();
        quizDTO.setId(quiz.getId());
        quizDTO.setTitle(quiz.getTitle());
        quizDTO.setDescription(quiz.getDescription());
        quizDTO.setCode(quiz.getCode());
        quizDTO.setCreated(quiz.getCreated());
        quizDTO.setTime(quiz.getTime());
        quizDTO.setTopic(quiz.getTopic());
        quizDTO.setImage(quiz.getImage());
        UserDTO userDTO= new UserDTO(quiz.getUser().getId(),quiz.getUser().getUsername(),quiz.getUser().getEmail());
        quizDTO.setUser(userDTO);
        int totalQuestion= quiz.getQuestions().size();
        quizDTO.setTotalQuestions(totalQuestion);
        return quizDTO;
    }
    public int findQuizByCode(String code) {
        Quiz quiz=quizRepository.findByCode(code).orElseThrow(()->new RuntimeException("Không Tìm Thấy Bài Thi"));
        int idQuiz=quiz.getId();
        return idQuiz;
    }
    public List<QuizDTO> getQuizListByIdTopic(int idTopic) {
        List<QuizDTO> quizDTOList=new ArrayList<>();
        List<Quiz> quizList=quizRepository.findAllByTopic_Id(idTopic);
        for(Quiz quiz:quizList){
            QuizDTO quizDTO=new QuizDTO();
            quizDTO.setId(quiz.getId());
            quizDTO.setTitle(quiz.getTitle());
            quizDTO.setDescription(quiz.getDescription());
            quizDTO.setCode(quiz.getCode());
            quizDTO.setCreated(quiz.getCreated());
            quizDTO.setTime(quiz.getTime());
            quizDTO.setTopic(quiz.getTopic());
            quizDTO.setImage(quiz.getImage());
            quizDTO.setTotalQuestions(quiz.getQuestions().size());
            quizDTO.setUser(new UserDTO(quiz.getUser().getId(),quiz.getUser().getUsername(),quiz.getUser().getEmail()));
            quizDTOList.add(quizDTO);
        }
        return quizDTOList;
    }

    public void deteleQuiz(int idQuiz) {
        quizRepository.deleteById(idQuiz);
    }
    public void updateQuiz(String title, String description, String topicName, int time, int idQuiz, int idUser, MultipartFile image) {
        Quiz quiz=quizRepository.findById(idQuiz).orElseThrow(()->new RuntimeException("Không Tìm Thấy Quiz"));
        if(idUser!=quiz.getUser().getId()){
            throw new RuntimeException("Không Đúng Người Tạo Quiz");
        }
        quiz.setTitle(title);
        quiz.setDescription(description);
        Topic topic=topicRepository.findByName(topicName).orElseThrow(()->new RuntimeException("Không Tìm Thấy Topic!"));
        quiz.setTopic(topic);
        if (image != null && !image.isEmpty()) {
            String imagePath = imageService.uploadImage(image);
            quiz.setImage(imagePath);
        }
        quiz.setTime(time);
        quizRepository.save(quiz);
    }
    public List<QuizDTO> getQuizListUser(int idUser) {
        List<Quiz> quizList=quizRepository.findAllByUser_Id(idUser);
        List<QuizDTO> quizDTOList=new ArrayList<>();
        for(Quiz quiz:quizList){
            QuizDTO quizDTO=new QuizDTO();
            quizDTO.setId(quiz.getId());
            quizDTO.setTitle(quiz.getTitle());
            quizDTO.setDescription(quiz.getDescription());
            quizDTO.setCode(quiz.getCode());
            quizDTO.setCreated(quiz.getCreated());
            quizDTO.setTime(quiz.getTime());
            quizDTO.setTopic(quiz.getTopic());
            quizDTO.setImage(quiz.getImage());
            quizDTO.setTotalQuestions(quiz.getQuestions().size());
            quizDTO.setUser(new UserDTO(quiz.getUser().getId(),quiz.getUser().getUsername(),quiz.getUser().getEmail()));
            quizDTOList.add(quizDTO);
        }
        return quizDTOList;
    }
    @Transactional
    public SubmitRespone submitQuiz(SubmitRequest submitRequest) {
        Quiz quiz= quizRepository.findById(submitRequest.getIdQuiz()).orElseThrow(()->new RuntimeException("Không Tìm Thấy Quiz"));
        User user=userRepository.findById(submitRequest.getIdUser()).orElseThrow(()->new RuntimeException("Không Tìm Thấy User!"));
        List <AnswerSelected> answerSelectedList=submitRequest.getAnswers();
        int score=0;
        int totalQuestion=quiz.getQuestions().size();
        List<UserResult> userResults = new ArrayList<>();
        for (AnswerSelected answerSelected:answerSelectedList){
            Question question= questionRepository.findById(answerSelected.getIdQuestion()).orElseThrow(()-> new RuntimeException("Không Tìm Thấy ID Question"+answerSelected.getIdQuestion()));
            boolean correctAnswer = false;
            Answer selectedAnswer=null;
            if (answerSelected.getIdAnswerSelected()!=null) {
                selectedAnswer = answerRepository.findById(answerSelected.getIdAnswerSelected()).orElseThrow(() -> new RuntimeException("Không Tìm Thấy Answer của Question" + answerSelected.getIdQuestion()));

                if (selectedAnswer.getQuestion().getId() != question.getId()) {
                    throw new RuntimeException("Câu Trả Lời Không Thuộc Về Câu Hỏi");
                } else {
                    if (selectedAnswer.isCorrect()) {
                        correctAnswer = true;
                        score++;
                    }
                }
            }
            UserResult userResult = new UserResult();
            userResult.setUser(user);
            userResult.setQuestion(question);
            userResult.setQuiz(quiz);
            userResult.setIs_correct(correctAnswer);
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
        for (UserResult userResult : userResults) {
            userResult.setQuizResult(quizResult);
        }
        userResultRepository.saveAll(userResults);
        SubmitRespone submitRespone=new SubmitRespone();
        submitRespone.setScore(score);
        submitRespone.setTotalQuestions(totalQuestion);
        return submitRespone;
    }

}
