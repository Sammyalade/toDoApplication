package africa.semicolon.toDoApplication.dtos.request;

import africa.semicolon.toDoApplication.datas.models.Priority;
import africa.semicolon.toDoApplication.datas.models.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UserTaskUpdateRequest {

    private int userId;
    private int taskId;
    private int notificationId;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDate dueDate;
    private LocalTime time;
    private String message;
    private boolean isRead;
}
