package chemquizAPI.controllers;

import chemquizAPI.common.ApiResponse;
import chemquizAPI.models.AdminLoginDTO;
import chemquizAPI.models.LeaderboardDTO;
import chemquizAPI.models.LobbyDataDTO;
import chemquizAPI.models.LoginUser;
import chemquizAPI.models.QuestionsDTO;
import chemquizAPI.models.ReactionQ;
import chemquizAPI.models.ResultsDTO;
import chemquizAPI.models.Score;
import chemquizAPI.models.ScoreDTO;
import chemquizAPI.models.StructureQ;
import chemquizAPI.models.StudentLoginDTO;
import chemquizAPI.models.SubmitDTO;
import chemquizAPI.models.User;
import chemquizAPI.models.UserDTO;
import chemquizAPI.repositories.ReactionRepository;
import chemquizAPI.repositories.ScoreRepository;
import chemquizAPI.repositories.StructureRepository;
import chemquizAPI.repositories.UserRepository;

import java.util.List;
import java.util.Random;

import org.springframework.http.MediaType;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping(path="/api")
public class LoginUserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private StructureRepository structureRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @GetMapping(value="/addDummyData")
    public void addDummyData() {
            User student = new User("student", "$2a$10$/GJcnmwt4TQQk6bDIhIad.V99/pmG3YqqMQzYB9yQ/Etx4I0YKiCm", false);
            User admin = new User("admin", "$2a$10$/GJcnmwt4TQQk6bDIhIad.V99/pmG3YqqMQzYB9yQ/Etx4I0YKiCm", true);

            userRepository.save(student);
            userRepository.save(admin);

            Score score1 = new Score(4, student);
            Score score2 = new Score(7, student);
            Score score3 = new Score(10, student);
            Score score4 = new Score(0, student);

            scoreRepository.save(score1);
            scoreRepository.save(score2);
            scoreRepository.save(score3);
            scoreRepository.save(score4);


        }

    @PostMapping(value="/login", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<UserDTO> logIn(@RequestParam String username, String password) {

        System.out.println("Find By Username");
        List<User> existingUsers = userRepository.findByUsername(username);

        if (existingUsers.isEmpty()) {
            return new ApiResponse<>(false, "User not found.", null);
        }

        User existingUser = existingUsers.get(0);

        boolean passwordCheck = existingUser.checkPassword(password);

        if (!passwordCheck) {
            return new ApiResponse<>(false, "Password didn't match.", null);
        }

        // UserDTO doesn't have password
        UserDTO userDTO = new UserDTO(existingUser.getUserId(), existingUser.getUsername(), existingUser.getIsAdmin());

        return new ApiResponse<>(true, "", userDTO);
    }

    @GetMapping(value="/welcome", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<LobbyDataDTO> getLobbyData(@RequestParam int userId) {

        Pageable pageable5 = PageRequest.of(0, 5);
        List<ScoreDTO> userScores = scoreRepository.findUserTopScores(userId, pageable5);

        int attemptCount = scoreRepository.findAttemptCount(userId);

        Pageable pageable4 = PageRequest.of(0, 4);
        List<LeaderboardDTO> leaderboardDTO = userRepository.findLeaderboardScores(pageable4);

        LobbyDataDTO lobbyDataDTO = new LobbyDataDTO(leaderboardDTO, attemptCount, userScores);

        return new ApiResponse<>(true, "", lobbyDataDTO);
    }

    @GetMapping(value="/questions", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<QuestionsDTO> getQuestions(@RequestParam boolean isDifficult) {
        System.out.println("is diffucult: " + isDifficult);
        Random random = new Random();
        int noStructureQs = random.nextInt(5) + 1;
        int noReactionQs = 5 - noStructureQs;

        System.out.println("RANDOMIZING...");
        System.out.println(noStructureQs);
        System.out.println(noReactionQs);

        List<StructureQ> structureQs = structureRepository.findStructureQ(isDifficult, noStructureQs);
        List<ReactionQ> reactionQs = reactionRepository.findReactionQ(isDifficult, noReactionQs);

        QuestionsDTO questionsDTO = new QuestionsDTO(structureQs, reactionQs);
        return new ApiResponse<QuestionsDTO>(true, null, questionsDTO);
    }

    @PostMapping(value="/submit", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<ResultsDTO> postScore(@RequestBody List<SubmitDTO> submitDTOs, @RequestParam int userId) {

        User user = userRepository.getReferenceById(userId);

        int studentScore = 0;
        boolean[] results = new boolean[submitDTOs.size()];

        // determine score
        for (int i = 0; i < submitDTOs.size(); i++) {
            SubmitDTO currentSubmit = submitDTOs.get(i);

            String answer = "";

            // get answer
            if (currentSubmit.getQuestionType().equals("structure")) {
                StructureQ currentQuestion = structureRepository.getReferenceById(currentSubmit.getQuestionId());
                answer = currentQuestion.getAnswer();
            }

            if (currentSubmit.getQuestionType().equals("reaction")) {
                ReactionQ currentQuestion = reactionRepository.getReferenceById(currentSubmit.getQuestionId());
                answer = currentQuestion.getProductInchi();
            }

            // check answer
            if (currentSubmit.getUserAnswer().equals(answer)) {
                studentScore++;
                results[i] = true;
            } else {
                results[i] = false;
            }
        }

        Score newScore = new Score(studentScore, user);
        scoreRepository.save(newScore);

        ResultsDTO resultsDTO = new ResultsDTO(studentScore, results);

        return new ApiResponse<ResultsDTO>(true, null, resultsDTO);
    }
}