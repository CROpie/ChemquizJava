package chemquizAPI.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "date_joined")
    private Date dateJoined;

    @Column(name = "is_admin")
    private boolean isAdmin;

    // mappedBy
    // the "user" field in the "Score" entity is responsible for the relationship
    // ie User doesn't have a FK to Score - Score has an FK to User
    // user refers to the field name in the Score entity - not a column name
    @OneToMany(mappedBy = "user", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Score> scores;

    // Spring needs a default constructor for some reason
    User() {}

    public User(String username, String password, Boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Date getDateJoined() {
        return this.dateJoined;
    }

    public Boolean getIsAdmin() {
        return this.isAdmin;
    }

    public List<Score> getScores() {
        return this.scores;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @PrePersist
    protected void onCreate() {
        this.dateJoined = new Date();
    }

    @Override
    public String toString() {
        return "{ " + this.username + " " + this.dateJoined + " " + this.isAdmin + " " + this.scores + " }";
    }

    public boolean checkPassword(String userInputPassword) {
        return BCrypt.checkpw(userInputPassword, this.password);
    }

    public String createPassword(String userInputPassword) {
        return BCrypt.hashpw(userInputPassword, BCrypt.gensalt());
    }
}
