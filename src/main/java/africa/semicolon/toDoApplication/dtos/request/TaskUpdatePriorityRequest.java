package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class TaskUpdatePriorityRequest {

    private int priorityNumber;
    private int taskId;
}
