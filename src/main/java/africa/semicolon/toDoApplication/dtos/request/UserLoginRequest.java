package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class UserLoginRequest {

    private String email;
    private int id;
}
