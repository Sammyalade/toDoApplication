package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class UserUpdateRequest {

    private String email;
    private String username;
    private int id;
}
