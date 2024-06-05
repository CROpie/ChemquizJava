package chemquizAPI.models;

public class AdminLoginDTO {
    private UserDTO userDTO;

    public AdminLoginDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUserDTO() {
        return this.userDTO;
    }
}
