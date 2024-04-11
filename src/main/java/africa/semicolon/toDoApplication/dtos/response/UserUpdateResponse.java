package africa.semicolon.toDoApplication.dtos.response;

import lombok.Data;

@Data
public class UserUpdateResponse {
    private int userId;
    private String username;
    private String email;
}
