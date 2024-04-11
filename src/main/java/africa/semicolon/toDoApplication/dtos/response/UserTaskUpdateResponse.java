package africa.semicolon.toDoApplication.dtos.response;

import lombok.Data;

@Data
public class UserTaskUpdateResponse {

    private int taskId;
    private int userId;
    private String taskTitle;

}
