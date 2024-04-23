package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class TaskUpdateTitleRequest {

    private String title;
    private int taskId;
}
