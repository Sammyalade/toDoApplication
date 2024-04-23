package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchByDueDateRequest {

    private LocalDate dueDate;
    private int userId;
}
