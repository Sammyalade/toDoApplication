package africa.semicolon.toDoApplication.dtos.response;

import lombok.Data;

@Data
public class OrganizationRegisterResponse {

    private int id;
    private String Name;
    private String email;
    private String Description;
}
