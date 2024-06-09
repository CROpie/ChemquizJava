package chemquizAPI.models;

import java.util.Date;

public class AdminUserDTO {
    private int userId;
    private String username;
    private Date dateJoined;
    private boolean isAdmin;

    public AdminUserDTO(int userId, String username, Date dateJoined, boolean isAdmin) {
        this.userId = userId;
        this.username = username;
        this.dateJoined = dateJoined;
        this.isAdmin = isAdmin;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }

    public Date getDateJoined() {
        return this.dateJoined;
    }

    public boolean getIsAdmin() {
        return this.isAdmin;
    }
}
