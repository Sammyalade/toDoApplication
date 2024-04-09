package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class NotificationUpdateRequest {

    private int id;
    private LocalTime time;
    private LocalDate date;
    private String message;
    private boolean isRead;
}
