package africa.semicolon.toDoApplication.dtos;

import lombok.Data;

@Data
public class UserUpdateRequest {

    public String email;
    private int id;
}
