package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class TaskUpdateStatusRequest {

    private int taskId;
    private int statusNumber;
}
