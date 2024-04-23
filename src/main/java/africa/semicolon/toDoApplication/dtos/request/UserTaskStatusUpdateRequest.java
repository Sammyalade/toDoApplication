package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class UserTaskStatusUpdateRequest {

    private int userId;
    private int taskId;
    private int statusNumber;
}
