package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class AddTaskToTaskListRequest {

    private int taskId;
    private long taskListId;
}
