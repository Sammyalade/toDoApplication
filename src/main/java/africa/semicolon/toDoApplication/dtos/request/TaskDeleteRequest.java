package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class TaskDeleteRequest {

    private int taskId;
    private int userId;
}
