package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class OrganizationLoginRequest {

    private String email;
    private int userId;
}
