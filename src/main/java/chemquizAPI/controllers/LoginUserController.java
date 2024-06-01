package chemquizAPI.controllers;

import chemquizAPI.common.ApiResponse;
import chemquizAPI.models.LoginUser;
import chemquizAPI.models.User;
import chemquizAPI.repositories.UserRepository;

import java.util.List;

import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(value="/login", produces=MediaType.APPLICATION_JSON_VALUE)
        public @ResponseBody ApiResponse<Void> logIn(@RequestParam String username, String password) {



        LoginUser loginUser = new LoginUser(username, password);
        System.out.println(loginUser);
        List<User> existingUser = userRepository.findByUsername(username);
        System.out.println(existingUser);


        // ApiResponse<Void> response = new ApiResponse<>(true, "Password match.", null);

        if (existingUser.isEmpty()) {
            return new ApiResponse<>(false, "User not found.", null);
        } 

        boolean passwordCheck = existingUser.get(0).checkPassword(password);

        if (!passwordCheck) {
            return new ApiResponse<>(false, "Password didn't match.", null);
        }

        return new ApiResponse<>(true, "", null);

    }
}
