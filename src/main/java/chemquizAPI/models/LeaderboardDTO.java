package chemquizAPI.models;

import java.util.Date;

/*
 * This is to get the Leaderboard information when logging in
 * Used a JPQL query joining the users and scores tables
 * Only required to have Constructor (no getters or setters)
 */

public class LeaderboardDTO {
    private int userId;
    private String username;
    private Date attemptDate;
    private int topScore;

    public LeaderboardDTO(int userId, String username, Date attemptDate, int topScore) {
        this.userId = userId;
        this.username = username;
        this.attemptDate = attemptDate;
        this.topScore = topScore;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }

    public Date getAttemptDate() {
        return this.attemptDate;
    }

    public int getTopScore() {
        return this.topScore;
    }

    @Override
    public String toString() {
        return "{ " + this.userId + " " + this.username + " " + this.attemptDate + " " + this.topScore + " }";
    }
}
