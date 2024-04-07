package africa.semicolon.toDoApplication.dtos;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.datas.models.Status;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskCreationRequest {

    private String title;
    private String description;
    private LocalDate dueDate;
    private Status status;
    private Notification notification;
}
