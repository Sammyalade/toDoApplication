package africa.semicolon.toDoApplication.dtos.response;

import lombok.Data;

@Data
public class AssignTaskToNewUserResponse {

    private String newUserId;
    private String assignorEmail;
    private String assignorUsername;
    private String assigneeEmail;
    private String taskName;
    private String taskId;

}
