package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class OrganizationTaskDeleteRequest {

    private int taskId;
    private int userId;
    private int organizationId;
}
