package africa.semicolon.toDoApplication.dtos;

import lombok.Data;

@Data
public class UserLoginRequest {

    private String email;
    private int id;
}
