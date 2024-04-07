package africa.semicolon.toDoApplication.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskNotificationTimeChangeRequest {

    private int id;
    private LocalDate time;
}
