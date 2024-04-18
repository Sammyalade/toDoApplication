package africa.semicolon.toDoApplication.dtos.request;

import africa.semicolon.toDoApplication.datas.models.Notification;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TaskAssignmentRequest {

    private String assignorEmail;
    private String assigneeEmail;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String notificationMessage;
    private Notification notification;
    private LocalTime notificationTime;
}
