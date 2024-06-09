package chemquizAPI.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import chemquizAPI.common.ApiResponse;
import chemquizAPI.models.AdminScoreDTO;
import chemquizAPI.models.AdminUserDTO;
import chemquizAPI.models.ReactionQ;
import chemquizAPI.models.Score;
import chemquizAPI.models.StructureQ;
import chemquizAPI.models.User;
import chemquizAPI.repositories.ReactionRepository;
import chemquizAPI.repositories.ScoreRepository;
import chemquizAPI.repositories.StructureRepository;
import chemquizAPI.repositories.UserRepository;

@Controller
@RequestMapping(path="/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private StructureRepository structureRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    // backdoor entry for when launching to an inaccessible database
    @GetMapping(value="/createadmin", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<?> createAdmin() {
        List<User> admin = userRepository.findByUsername("admin");

        if (!admin.isEmpty()) {
            return new ApiResponse<>(false, "Admin is already in the system.", null);
        }

        User newAdmin = new User("admin", "$2a$10$/GJcnmwt4TQQk6bDIhIad.V99/pmG3YqqMQzYB9yQ/Etx4I0YKiCm", true);

        userRepository.save(newAdmin);

        return new ApiResponse<>(true, "admin has been created", null);
    }

    @GetMapping(value="/users", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<List<AdminUserDTO>> getUsers() {
        List<AdminUserDTO> adminUserDTOs = userRepository.getAdminUserDTOs();
        return new ApiResponse<List<AdminUserDTO>>(true, null, adminUserDTOs);
    }

    @PostMapping(value="/users", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<?> postUser(@RequestBody User inputUser) {
        List<User> existingUser = userRepository.findByUsername(inputUser.getUsername());

        if (!existingUser.isEmpty()) {
            return new ApiResponse<>(false, "That username already exists.", null);    
        }

        String hashedPassword = inputUser.createPassword(inputUser.getPassword());
        inputUser.setPassword(hashedPassword);

        userRepository.save(inputUser);

        List<AdminUserDTO> adminUserDTOs = userRepository.getAdminUserDTOs();
        
        return new ApiResponse<List<AdminUserDTO>>(true, null, adminUserDTOs);
    }

    // username (and potentially other non-password values)
    @PatchMapping(value="/users", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<List<AdminUserDTO>> patchUser(@RequestBody AdminUserDTO inputAdminUserDTO) {
        User existingUser = userRepository.getReferenceById(inputAdminUserDTO.getUserId());

        // only allowing changing of username, but id, username, dateJoined and isAdmin could all be easily changed here
        existingUser.setUsername(inputAdminUserDTO.getUsername());
        userRepository.save(existingUser);

        List<AdminUserDTO> adminUserDTOs = userRepository.getAdminUserDTOs();
        return new ApiResponse<List<AdminUserDTO>>(true, null, adminUserDTOs);
    }

    // user password
    @PutMapping(value="/users", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<List<AdminUserDTO>> patchPassword(@RequestBody User inputUser) {
        User existingUser = userRepository.getReferenceById(inputUser.getUserId());

        String hashedPassword = existingUser.createPassword(inputUser.getPassword());

        // only allowing changing of username, but id, username, dateJoined and isAdmin could all be easily changed here
        existingUser.setPassword(hashedPassword);
        userRepository.save(existingUser);

        List<AdminUserDTO> adminUserDTOs = userRepository.getAdminUserDTOs();
        return new ApiResponse<List<AdminUserDTO>>(true, "Password changed successfully.", adminUserDTOs);
    }

    @DeleteMapping(value="/users", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<List<AdminUserDTO>> deleteUser(@RequestParam int userId) {

        userRepository.deleteById(userId);

        List<AdminUserDTO> adminUserDTOs = userRepository.getAdminUserDTOs();
        return new ApiResponse<List<AdminUserDTO>>(true, null, adminUserDTOs);
    }

    @GetMapping(value="/scores", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<List<AdminScoreDTO>> getScores() {
        List<AdminScoreDTO> adminScoreDTOs = scoreRepository.getAdminScoreDTOs();
        return new ApiResponse<List<AdminScoreDTO>>(true, null, adminScoreDTOs);
    }

    @PatchMapping(value="/scores", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<List<AdminScoreDTO>> patchScore(@RequestBody AdminScoreDTO inputAdminScoreDTO) {
        Score existingScore = scoreRepository.getReferenceById(inputAdminScoreDTO.getGameId());
        existingScore.setScoreValue(inputAdminScoreDTO.getScoreValue());
        scoreRepository.save(existingScore);

        List<AdminScoreDTO> adminScoreDTOs = scoreRepository.getAdminScoreDTOs();
        return new ApiResponse<List<AdminScoreDTO>>(true, null, adminScoreDTOs);
    }

    @DeleteMapping(value="/scores", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<List<AdminScoreDTO>> deleteScore(@RequestParam int gameId) {

        scoreRepository.deleteById(gameId);

        List<AdminScoreDTO> adminScoreDTOs = scoreRepository.getAdminScoreDTOs();
        return new ApiResponse<List<AdminScoreDTO>>(true, null, adminScoreDTOs);
    }

    @GetMapping(value="/structure", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<List<StructureQ>> getStructureQs() {
        List<StructureQ> structureQs = structureRepository.findAll();
        return new ApiResponse<List<StructureQ>>(true, null, structureQs);
    }

    @PutMapping(value="/structure", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<List<StructureQ>> postStructureQs(@RequestBody StructureQ inputStructureQ) {

        System.out.println(inputStructureQ.toString());

        // int intializes to 0
        // for new questions, structureId == 0 so will be inserted into the database
        if (inputStructureQ.getStructureId() == 0) {
            structureRepository.save(inputStructureQ);
        } else {
            StructureQ existingStructureQ = structureRepository.getReferenceById(inputStructureQ.getStructureId());
            existingStructureQ.setMolecule(inputStructureQ.getMolecule());
            existingStructureQ.setAnswer(inputStructureQ.getAnswer());
            existingStructureQ.setIncorrect1(inputStructureQ.getIncorrect1());
            existingStructureQ.setIncorrect2(inputStructureQ.getIncorrect2());
            existingStructureQ.setIncorrect3(inputStructureQ.getIncorrect3());
            existingStructureQ.setIsDifficult(inputStructureQ.getIsDifficult());
            structureRepository.save(existingStructureQ);

            // need to call this to ensure changes are persisted before calling findAll() - but doesn't currently work
            structureRepository.flush();
        }

        List<StructureQ> structureQs = structureRepository.findAll();
        return new ApiResponse<List<StructureQ>>(true, null, structureQs);
    }

    @DeleteMapping(value="/structure", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<List<StructureQ>> deleteStructureQs(@RequestParam int structureId) {

        structureRepository.deleteById(structureId);

        List<StructureQ> structureQs = structureRepository.findAll();
        return new ApiResponse<List<StructureQ>>(true, null, structureQs);
    }

    @GetMapping(value="/reaction", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<List<ReactionQ>> getReactionQs() {
        List<ReactionQ> reactionQs = reactionRepository.findAll();
        System.out.println(reactionQs);
        return new ApiResponse<List<ReactionQ>>(true, null, reactionQs);
    }

    @PutMapping(value="/reaction", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<List<ReactionQ>> postReactionQs(@RequestBody ReactionQ inputReactionQ) {

        System.out.println(inputReactionQ.toString());

        // int intializes to 0
        // for new questions, structureId == 0 so will be inserted into the database
        if (inputReactionQ.getReactionId() == 0) {
            reactionRepository.save(inputReactionQ);
        } else {
            ReactionQ existingReactionQ = reactionRepository.getReferenceById(inputReactionQ.getReactionId());
            existingReactionQ.setReactant(inputReactionQ.getReactant());
            existingReactionQ.setReagent(inputReactionQ.getReagent());
            existingReactionQ.setProductSmile(inputReactionQ.getProductSmile());
            existingReactionQ.setProductInchi(inputReactionQ.getProductInchi());
            existingReactionQ.setCatalyst(inputReactionQ.getCatalyst());
            existingReactionQ.setSolvent(inputReactionQ.getSolvent());
            existingReactionQ.setTemperature(inputReactionQ.getTemperature());
            existingReactionQ.setTime(inputReactionQ.getTime());
            existingReactionQ.setIsDifficult(inputReactionQ.getIsDifficult());
            reactionRepository.save(existingReactionQ);

            // need to call this to ensure changes are persisted before calling findAll() - but doesn't currently work
            reactionRepository.flush();
        }

        // for some reason, scores is updated when calling getAdminScoreDTOs() after one is modified.
        // something is different with the default method findAll() ?

        List<ReactionQ> reactionQs = reactionRepository.findAll();
        return new ApiResponse<List<ReactionQ>>(true, null, reactionQs);
    }

    @DeleteMapping(value="/reaction", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<List<ReactionQ>> deleteReactionQs(@RequestParam int reactionId) {

        reactionRepository.deleteById(reactionId);

        List<ReactionQ> reactionQs = reactionRepository.findAll();
        return new ApiResponse<List<ReactionQ>>(true, null, reactionQs);
    }
}
