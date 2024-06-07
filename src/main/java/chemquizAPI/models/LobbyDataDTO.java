package chemquizAPI.models;

import java.io.Serializable;
import java.util.List;

public class LobbyDataDTO implements Serializable{
    private List<LeaderboardDTO> leaderboardDTOs;
    private int attemptCount;
    private List<ScoreDTO> userScores;

    public LobbyDataDTO(List<LeaderboardDTO> leaderboardDTOs, int attemptCount, List<ScoreDTO> userScores) {
        this.leaderboardDTOs = leaderboardDTOs;
        this.attemptCount = attemptCount;
        this.userScores = userScores;
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
