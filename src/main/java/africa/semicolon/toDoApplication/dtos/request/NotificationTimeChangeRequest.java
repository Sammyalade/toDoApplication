package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationTimeChangeRequest {

    private int id;
    private LocalDateTime dateTime;
}
