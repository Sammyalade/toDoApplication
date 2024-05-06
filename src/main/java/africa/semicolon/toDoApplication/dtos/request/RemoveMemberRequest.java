package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class RemoveMemberRequest {

    private long organizationId;
    private int userId;
}
