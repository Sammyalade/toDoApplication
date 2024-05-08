package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class GetAllMembersRequest {

    private long organizationId;
    private String email;
}
