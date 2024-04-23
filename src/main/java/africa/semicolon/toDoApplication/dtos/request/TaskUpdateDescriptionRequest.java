package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class TaskUpdateDescriptionRequest {

    private int taskId;
    private String description;
}
