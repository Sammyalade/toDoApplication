package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserTaskDueDateUpdateRequest {
    private int taskId;
    private int userId;
    private LocalDate dueDate;
}
