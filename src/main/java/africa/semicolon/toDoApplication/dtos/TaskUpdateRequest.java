package africa.semicolon.toDoApplication.dtos;

import africa.semicolon.toDoApplication.datas.models.Status;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskUpdateRequest {

    private int id;
    private Status status;
    private LocalDate dueDate;

}
