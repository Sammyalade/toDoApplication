package africa.semicolon.toDoApplication.services.organizationService;

import africa.semicolon.toDoApplication.datas.models.Organization;
import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.User;
import africa.semicolon.toDoApplication.datas.repositories.OrganizationRepository;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.dtos.response.OrganizationUpdateResponse;
import africa.semicolon.toDoApplication.dtos.response.TaskCreationResponse;
import africa.semicolon.toDoApplication.exceptions.*;
import africa.semicolon.toDoApplication.services.EmailService;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
import africa.semicolon.toDoApplication.services.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static africa.semicolon.toDoApplication.utility.Mapper.map;
import static africa.semicolon.toDoApplication.utility.Utility.isEmptyOrNullString;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;
    private  OrganizationRegisterRequest organizationRegisterRequest;

    @Override
    public void startRegistration(OrganizationRegisterRequest organizationRegisterRequest){
        this.organizationRegisterRequest = organizationRegisterRequest;
        checkOrganizationName(organizationRegisterRequest);
        checkOrganizationalEmail(organizationRegisterRequest);
        emailService.sendVerificationEmail(organizationRegisterRequest.getOrganizationEmail(), organizationRegisterRequest.getOrganizationName());
    }

    @Override
    public OrganizationUpdateResponse completeRegistration(String verificationCode){
        if(verificationCode.equals(emailService.getVerificationCode())) {
            Organization organization = map(organizationRegisterRequest);
            organizationRepository.save(organization);
            return (OrganizationUpdateResponse) map(organization);
        }
        throw new VerificationFailedException("Incorrect Verification Code. Please enter a correct code and try again");
    }





    private void checkOrganizationalEmail(OrganizationRegisterRequest organizationRegisterRequest) {
        for (Organization organization : organizationRepository.findAll()){
            if (organization.getEmail().equals(organizationRegisterRequest.getOrganizationEmail())){
                throw new TodoApplicationException("Organization Email cannot be used");
            }
        }
    }

    private void checkOrganizationName(OrganizationRegisterRequest organizationRegisterRequest) {
        for (Organization organization : organizationRepository.findAll()){
            if (organization.getName().equals(organizationRegisterRequest.getOrganizationName().toLowerCase())){
                throw new TodoApplicationException("Organization already registered");
            }
        }
    }


    @Override
    public OrganizationUpdateResponse updateOrganizationName(OrganizationUpdateRequest organizationUpdateRequest){
        if(isEmptyOrNullString(organizationUpdateRequest.getString())) throw new EmptyStringException("Name cannot be empty");
        Organization organization = searchForOrganization(organizationUpdateRequest.getId());
        organization.setName(organizationUpdateRequest.getString());
        organizationRepository.save(organization);
        return (OrganizationUpdateResponse) map(organization);
    }

    @Override
    public OrganizationUpdateResponse updateOrganizationDescription(OrganizationUpdateRequest organizationUpdateRequest){
        if(isEmptyOrNullString(organizationUpdateRequest.getString())) throw new EmptyStringException("Description cannot be empty");
        Organization organization = searchForOrganization(organizationUpdateRequest.getId());
        organization.setDescription(organizationUpdateRequest.getString());
        organizationRepository.save(organization);
        return (OrganizationUpdateResponse) map(organization);
    }



    @Override
    public Organization searchForOrganization(long id){
        return organizationRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found"));
        }

    @Override
    public void addMoreMember(AddMemberRequest addMemberRequest){
        Organization organization = searchForOrganization(addMemberRequest.getOrganizationId());
        organization.getMembers().add(userService.searchUserById(addMemberRequest.getUserId()));
        organizationRepository.save(organization);
    }

    @Override
    public void removeMember(RemoveMemberRequest removeMemberRequest){
        Organization organization = searchForOrganization(removeMemberRequest.getOrganizationId());
        organization.getMembers().remove(userService.searchUserById(removeMemberRequest.getUserId()));
        organizationRepository.save(organization);
    }

    @Override
    public TaskCreationResponse addTaskToOrganization(OrganizationTaskAddRequest organizationTaskAddRequest) {
        Task task = taskService.createTask(map(organizationTaskAddRequest));
        Organization organization = searchForOrganization(organizationTaskAddRequest.getOrganizationId());
        organization.getTasks().add(task);
        return map(task.getId(), task.getTitle(), task.getNotification().getId());
    }


    @Override
    public void deleteTask(OrganizationTaskDeleteRequest request){
        Organization organization = searchForOrganization(request.getOrganizationId());
        if(!organization.getMembers().contains(userService.searchUserById(request.getUserId())))
            throw new TodoApplicationException("Email does not match");

        if(organization.getTasks().contains(taskService.searchForTaskById(request.getTaskId()))){
            organization.getTasks().remove(request.getTaskId());
            taskService.deleteTask(request.getTaskId());
        }
    }

    @Override
    public List<Task> getOrganizationTask(GetOrganizationTaskRequest request){
        User user = userService.searchUserById(request.getUserId());
        Organization organization = searchForOrganization(request.getOrganizationId());
        if(organization.getMembers().contains(user)){
            return organization.getTasks();
        }
        throw new UserNotFoundException("You are not member of this organization");
    }
}
