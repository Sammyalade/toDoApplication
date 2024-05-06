package africa.semicolon.toDoApplication.dtos.request;

import lombok.Data;

@Data
public class OrganizationRegisterRequest {

    private String organizationName;
    private String organizationDescription;
    private String organizationEmail;
    private int registrarId;
}
