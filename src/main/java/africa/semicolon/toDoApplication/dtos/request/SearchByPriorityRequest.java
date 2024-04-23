package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class SearchByPriorityRequest {

    private int priorityNumber;
    private int userId;
}
