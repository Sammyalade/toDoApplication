package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

import java.time.LocalTime;

@Data
public class TaskNotificationUpdateRequest {

    private int taskId;
    private LocalTime notificationTime;
}
