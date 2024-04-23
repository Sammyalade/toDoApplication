package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class UserTaskTitleUpdateRequest {
    private String title;
    private int userId;
    private int taskId;
}
