package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class GetOrganizationTaskRequest {

    private int organizationId;
    private int userId;
}
