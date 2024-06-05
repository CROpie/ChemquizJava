package chemquizAPI.models;

import java.io.Serializable;
import java.util.List;

public class StudentLoginDTO implements Serializable{
    private UserDTO userDTO;
    private List<LeaderboardDTO> leaderboardDTOs;
    private int attemptCount;
    private List<ScoreDTO> userScores;

    public StudentLoginDTO(UserDTO userDTO, List<LeaderboardDTO> leaderboardDTOs, int attemptCount, List<ScoreDTO> userScores) {
        this.userDTO = userDTO;
        this.leaderboardDTOs = leaderboardDTOs;
        this.attemptCount = attemptCount;
        this.userScores = userScores;
    }

    public UserDTO getUserDTO() {
        return this.userDTO;
    }

    public List<LeaderboardDTO> getLeaderboardDTOs() {
        return this.leaderboardDTOs;
    }

    public int getAttemptCount() {
        return this.attemptCount;
    }

    public List<ScoreDTO> getUserScores() {
        return this.userScores;
    }

}
