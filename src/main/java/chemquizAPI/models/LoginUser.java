package chemquizAPI.models;

public class LoginUser {
    private String username;
    private String password;

    public LoginUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    String getUsername() {
        return this.username;
    }

    String getPassword() {
        return this.password;
    }


}
