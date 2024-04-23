package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskUpdateDueDateRequest {
    private int taskId;
    private LocalDate dueDate;
}
