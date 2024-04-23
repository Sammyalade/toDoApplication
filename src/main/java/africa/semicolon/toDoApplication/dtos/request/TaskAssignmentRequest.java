package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TaskAssignmentRequest {

    private String assignorUsername;
    private String assigneeUsername;
    private String assignorEmail;
    private String assigneeEmail;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String notificationMessage;
    private LocalTime notificationTime;
}
