package africa.semicolon.toDoApplication.dtos.response;

import lombok.Data;

@Data
public class UserRegistrationResponse {

    private int userId;
    private int taskListId;
    private String username;
    private String email;

}
