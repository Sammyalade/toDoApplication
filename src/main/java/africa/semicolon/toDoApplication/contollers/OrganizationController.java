package africa.semicolon.toDoApplication.contollers;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.dtos.response.OrganizationRegisterResponse;
import africa.semicolon.toDoApplication.dtos.response.OrganizationUpdateResponse;
import africa.semicolon.toDoApplication.dtos.response.TaskCreationResponse;
import africa.semicolon.toDoApplication.dtos.response.UserApiResponse;
import africa.semicolon.toDoApplication.exceptions.TodoApplicationException;
import africa.semicolon.toDoApplication.services.organizationService.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/startRegistration")
    public ResponseEntity<?> startRegistration(@RequestBody OrganizationRegisterRequest organizationRegisterRequest) {
        try{
            organizationService.startRegistration(organizationRegisterRequest);
            return new ResponseEntity<>(new UserApiResponse(true, "Please enter the Verification code to continue"), CREATED);
        } catch (TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/completeRegistration/{verificationCode}")
    public ResponseEntity<?> completeRegistration(@PathVariable("verificationCode") String verificationCode) {
        try {
            OrganizationRegisterResponse response = organizationService.completeRegistration(verificationCode);
            return new ResponseEntity<>(new UserApiResponse(true, response), CREATED);
        } catch (TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/updateOrganizationName")
    public ResponseEntity<?> updateOrganizationName(@RequestBody OrganizationUpdateRequest organizationUpdateRequest) {
        try{
            OrganizationUpdateResponse response = organizationService.updateOrganizationName(organizationUpdateRequest);
            return new ResponseEntity<>(new UserApiResponse(true, response), OK);
        } catch (TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/updateOrganizationDescription")
    public ResponseEntity<?> updateOrganizationDescription(@RequestBody OrganizationUpdateRequest organizationUpdateRequest) {
        try{
            OrganizationUpdateResponse response = organizationService.updateOrganizationDescription(organizationUpdateRequest);
            return new ResponseEntity<>(new UserApiResponse(true, response), OK);
        } catch (TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/addMember")
    public ResponseEntity<?> addMember(@RequestBody AddMemberRequest addMemberRequest){
        try{
            organizationService.addMoreMember(addMemberRequest);
            return new ResponseEntity<>(new UserApiResponse(true, "Member Successfully added"), OK);
        } catch (TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/removeMember")
    public ResponseEntity<?> removeMember(@RequestBody RemoveMemberRequest removeMemberRequest){
        try{
            organizationService.removeMember(removeMemberRequest);
            return new ResponseEntity<>(new UserApiResponse(true, "Member Successfully Removed"), OK);
        } catch (TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/createTask")
    public ResponseEntity<?> createTask(@RequestBody OrganizationTaskAddRequest organizationTaskAddRequest){
        try{
            TaskCreationResponse response = organizationService.addTaskToOrganization(organizationTaskAddRequest);
            return new ResponseEntity<>(new UserApiResponse(true, response), CREATED);
        } catch (TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/deleteTask")
    public ResponseEntity<?> deleteTask(@RequestBody OrganizationTaskDeleteRequest request){
        try{
            organizationService.deleteTask(request);
            return new ResponseEntity<>(new UserApiResponse(true, "Task Successfully Deleted"), OK);
        } catch (TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/getOrganizationTask")
    public ResponseEntity<?> getOrganizationTask(@RequestBody GetOrganizationTaskRequest request){
        try{
            List<Task> tasks = organizationService.getOrganizationTask(request);
            return new ResponseEntity<>(new UserApiResponse(true, tasks), OK);
        } catch (TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }







}
