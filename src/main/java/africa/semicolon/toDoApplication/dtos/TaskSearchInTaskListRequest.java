package africa.semicolon.toDoApplication.dtos;

import lombok.Data;

@Data
public class TaskSearchInTaskListRequest {

    private long taskId;
    private long taskListId;
}
