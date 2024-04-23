package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class UserTaskPriorityUpdateRequest {

    private int userId;
    private int taskId;
    private int priorityNumber;
}
