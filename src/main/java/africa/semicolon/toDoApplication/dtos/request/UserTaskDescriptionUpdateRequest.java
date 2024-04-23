package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class UserTaskDescriptionUpdateRequest {

    private String description;
    private int taskId;
    private int userId;
}
