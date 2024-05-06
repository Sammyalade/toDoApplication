package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class OrganizationTaskAddRequest {

    private String title;
    private String description;
    private LocalDate dueDate;
    private String notificationMessage;
    private LocalTime notificationTime;
    private long organizationId;
}
