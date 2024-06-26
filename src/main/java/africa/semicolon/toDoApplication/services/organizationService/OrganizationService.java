package africa.semicolon.toDoApplication.services.organizationService;

import africa.semicolon.toDoApplication.datas.models.Organization;
import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.dtos.response.OrganizationRegisterResponse;
import africa.semicolon.toDoApplication.dtos.response.TaskCreationResponse;

import java.util.List;

public interface OrganizationService {

    void startRegistration(OrganizationRegisterRequest organizationRegisterRequest);

    OrganizationRegisterResponse completeRegistration(String verificationCode);

    void login(OrganizationLoginRequest organizationLoginRequest);

    void logout(int userId);

    OrganizationRegisterResponse updateOrganizationName(OrganizationUpdateRequest organizationUpdateRequest);

    OrganizationRegisterResponse updateOrganizationDescription(OrganizationUpdateRequest organizationUpdateRequest);

    Organization searchForOrganization(long id);

    void addMoreMember(AddMemberRequest addMemberRequest);

    void removeMember(RemoveMemberRequest removeMemberRequest);

    TaskCreationResponse addTaskToOrganization(OrganizationTaskAddRequest organizationTaskAddRequest);

    void deleteTask(OrganizationTaskDeleteRequest request);

    List<Task> getOrganizationTask(GetOrganizationTaskRequest request);

    List<String> getAllOrganizationMember(GetAllMembersRequest getAllMembersRequest);
}
