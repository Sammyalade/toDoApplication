package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class SearchByStatusRequest {

    private int statusNumber;
    private int userId;
}
