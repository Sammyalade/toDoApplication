package africa.semicolon.toDoApplication.dtos.request;

import africa.semicolon.toDoApplication.datas.models.Notification;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TaskCreationRequest {

    private String title;
    private String description;
    private LocalDate dueDate;
    private Notification notification;
    private LocalTime notificationTime;
}
