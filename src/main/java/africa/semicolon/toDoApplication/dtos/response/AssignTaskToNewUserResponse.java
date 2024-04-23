package africa.semicolon.toDoApplication.dtos.response;

import lombok.Data;

@Data
public class AssignTaskToNewUserResponse {

    private int newUserId;
    private String assignorUsername;
    private String assigneeUsername;
    private String taskName;
    private int taskId;

}
