package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class TaskAcceptanceRequest {
    private int userId;
    private String email;
    private String username;
}
