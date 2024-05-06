package africa.semicolon.toDoApplication.dtos.request;

import africa.semicolon.toDoApplication.datas.models.User;
import lombok.Data;

@Data
public class OrganizationRegisterRequest {

    private String organizationName;
    private String organizationDescription;
    private String organizationEmail;
    private User registrar;
}
