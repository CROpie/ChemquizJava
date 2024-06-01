package chemquizAPI.models;

import java.util.Date;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import org.mindrot.jbcrypt.BCrypt;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    private String username;
    private String password;
    private Date dateJoined;
    private boolean isAdmin;

    // id and dateJoined are added automatically when inserted into the database

    User() {}

    User(String username, String password, boolean isAdmin) {
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

    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    @Override
    public String toString() {
        return "{ " + this.username + " " + this.dateJoined + " " + this.isAdmin + " }";
    }

    public boolean checkPassword(String userInputPassword) {
        return BCrypt.checkpw(userInputPassword, this.password);
    }

    public String createPassword(String userInputPassword) {
        return BCrypt.hashpw(userInputPassword, BCrypt.gensalt());
    }
}
