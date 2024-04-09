package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class UserRegistrationRequest {

    private String username;
    private String email;

}
