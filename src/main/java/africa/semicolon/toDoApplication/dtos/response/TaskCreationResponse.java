package africa.semicolon.toDoApplication.dtos.response;

import lombok.Data;

@Data
public class TaskCreationResponse {

    private int taskId;
    private int notificationId;
    private String taskTitle;
}
