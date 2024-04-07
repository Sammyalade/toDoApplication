package africa.semicolon.toDoApplication.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationTimeChangeRequest {

    private int id;
    private LocalDateTime dateTime;
}
