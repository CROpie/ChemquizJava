package chemquizAPI.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "scores")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private int gameId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "score_value")
    private int scoreValue;

    @Column(name = "attempt_date")
    private Date attemptDate;

    Score() {}

    public Score(int scoreValue, User user) {
        this.scoreValue = scoreValue;
        this.user = user;
    }

    public int getGameId() {
        return this.gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getScoreValue() {
        return this.scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @PrePersist
    protected void onCreate() {
        this.attemptDate = new Date();
    }

    @Override
    public String toString() {
        return "{ " + this.scoreValue + " }";
    }

}