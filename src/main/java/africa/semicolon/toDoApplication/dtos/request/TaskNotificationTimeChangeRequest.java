package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskNotificationTimeChangeRequest {

    private int id;
    private LocalDate time;
}
