package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class SearchByTitleRequest {

    private String title;
    private int userId;
}
