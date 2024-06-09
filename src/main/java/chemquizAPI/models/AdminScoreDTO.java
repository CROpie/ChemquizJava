package chemquizAPI.models;

import java.util.Date;

public class AdminScoreDTO {
    private int gameId;
    private String username;
    private int scoreValue;
    private Date attemptDate;

    public AdminScoreDTO(int gameId, int scoreValue, Date attemptDate, String username) {
        this.gameId = gameId;
        this.username = username;
        this.scoreValue = scoreValue;
        this.attemptDate = attemptDate;
    }

    public int getGameId() {
        return this.gameId;
    }

    public String getUsername() {
        return this.username;
    }

    public int getScoreValue() {
        return this.scoreValue;
    }

    public Date getAttemptDate() {
        return this.attemptDate;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    public void setAttemptDate(Date attemptDate) {
        this.attemptDate = attemptDate;
    }
}

// public class AdminScoreDTO {
//     private int gameId;
//     private int scoreValue;
//     private Date attemptDate;

//     public AdminScoreDTO(int gameId, int scoreValue, Date attemptDate) {
//         this.gameId = gameId;
//         this.scoreValue = scoreValue;
//         this.attemptDate = attemptDate;
//     }

//     public int getGameId() {
//         return this.gameId;
//     }

//     public int getScoreValue() {
//         return this.scoreValue;
//     }

//     public Date getAttemptDate() {
//         return this.attemptDate;
//     }
// }
