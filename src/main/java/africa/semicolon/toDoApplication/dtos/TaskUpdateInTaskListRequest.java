package africa.semicolon.toDoApplication.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TaskUpdateInTaskListRequest extends TaskUpdateRequest {

    private long taskListId;

}
