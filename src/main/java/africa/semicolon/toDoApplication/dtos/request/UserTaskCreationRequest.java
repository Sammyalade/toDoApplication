package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UserTaskCreationRequest {

    private String title;
    private String description;
    private LocalDate dueDate;
    private String notificationMessage;
    private LocalTime notificationTime;
    private int taskListId;
    private int userId;
}
