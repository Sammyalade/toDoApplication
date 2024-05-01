package africa.semicolon.toDoApplication.dtos.response;

import lombok.Data;

@Data
public class AssignTaskToOldUserResponse {

    private int userId;
    private String taskTitle;
    private String assigneeUsername;
    private String assignorUsername;
}
