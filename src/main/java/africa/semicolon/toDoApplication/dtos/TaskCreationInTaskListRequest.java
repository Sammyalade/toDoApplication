package africa.semicolon.toDoApplication.dtos;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.datas.models.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TaskCreationInTaskListRequest extends TaskCreationRequest{

    private int id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Status status;
    private Notification notification;
    private LocalTime notificationTime;
}
