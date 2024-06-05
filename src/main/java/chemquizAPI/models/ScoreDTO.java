package chemquizAPI.models;

import java.util.Date;

public class ScoreDTO {

    private int scoreValue;
    private Date attemptDate;

    public ScoreDTO(int scoreValue, Date attemptDate) {
        this.scoreValue = scoreValue;
        this.attemptDate = attemptDate;
    }

    public int getScoreValue() {
        return this.scoreValue;
    }

    public Date getAttemptDate() {
        return this.attemptDate;
    }
}