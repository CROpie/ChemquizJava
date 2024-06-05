package chemquizAPI.controllers;

import chemquizAPI.common.ApiResponse;
import chemquizAPI.models.AdminLoginDTO;
import chemquizAPI.models.LeaderboardDTO;
import chemquizAPI.models.LoginUser;
import chemquizAPI.models.Score;
import chemquizAPI.models.ScoreDTO;
import chemquizAPI.models.StudentLoginDTO;
import chemquizAPI.models.User;
import chemquizAPI.models.UserDTO;
import chemquizAPI.repositories.ScoreRepository;
import chemquizAPI.repositories.UserRepository;

import java.util.List;

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

    // @GetMapping(value="/password", produces=MediaType.APPLICATION_JSON_VALUE)
    //     public ApiResponse<String> hashPass(@RequestParam String pass) {
    //         String hashedPassword = BCrypt.hashpw(pass, BCrypt.gensalt());
    //         System.out.println(hashedPassword);
    //         return new ApiResponse<>(true, "", hashedPassword);
    //     }

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

    // ApiResponse<?> allows for all of the possibilities: null (reject), UserDTO (admin), StudentLoginDTO (student)
    @PostMapping(value="/login", produces=MediaType.APPLICATION_JSON_VALUE)
        public @ResponseBody ApiResponse<?> logIn(@RequestParam String username, String password) {

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

        if (existingUser.getIsAdmin()) {
            AdminLoginDTO adminLoginDTO = new AdminLoginDTO(userDTO);
            return new ApiResponse<>(true, "", adminLoginDTO);
        }
        
        System.out.println("Find User Scores");
        Pageable pageable5 = PageRequest.of(0, 5);
        List<ScoreDTO> userScores = scoreRepository.findUserTopScores(existingUser.getUserId(), pageable5);

        System.out.println(userScores);

        System.out.println("Find attempt count");
        int attemptCount = scoreRepository.findAttemptCount(existingUser.getUserId());

        System.out.println(attemptCount);

        System.out.println("Find leaderboard");
        Pageable pageable4 = PageRequest.of(0, 4);
        List<LeaderboardDTO> leaderboardDTO = userRepository.findLeaderboardScores(pageable4);

        System.out.println(leaderboardDTO);

        StudentLoginDTO studentLoginDTO = new StudentLoginDTO(userDTO, leaderboardDTO, attemptCount, userScores);

        return new ApiResponse<>(true, "", studentLoginDTO);
    }
}