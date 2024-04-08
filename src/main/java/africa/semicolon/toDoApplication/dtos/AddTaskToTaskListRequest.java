package africa.semicolon.toDoApplication.dtos;

import lombok.Data;

@Data
public class AddTaskToTaskListRequest {

    private int taskId;
    private long taskListId;
}
