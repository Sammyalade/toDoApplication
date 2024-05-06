package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class OrganizationUpdateRequest {

    private long id;
    private String string;
    private String email;
}
