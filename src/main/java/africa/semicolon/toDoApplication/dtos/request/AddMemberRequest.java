package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class AddMemberRequest {

    private long organizationId;
    private int userId;
}
