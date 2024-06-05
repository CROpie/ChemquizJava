package chemquizAPI.models;

public class UserDTO {
    private int userId;
    private String username;
    private boolean isAdmin;

    public UserDTO(int userId, String username, boolean isAdmin) {
        this.userId = userId;
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean getIsAdmin() {
        return this.isAdmin;
    }
}
