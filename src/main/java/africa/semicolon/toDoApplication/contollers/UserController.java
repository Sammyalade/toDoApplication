package africa.semicolon.toDoApplication.contollers;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.dtos.response.*;
import africa.semicolon.toDoApplication.exceptions.TodoApplicationException;
import africa.semicolon.toDoApplication.services.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.StringTemplate.STR;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/toDoApplication")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String home(){
        return ("<h1>To Do Application</h1>");
    }

    @PostMapping("/startRegistration")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        try{
           userService.startRegistration(userRegistrationRequest);
            return new ResponseEntity<>(new UserApiResponse(true, "Please verify your email to continue"), CREATED);
        }
        catch(TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/completeRegistration/{verificationCode}")
    public ResponseEntity<?> completeRegistration(@PathVariable("verificationCode") String verificationCode) {
        try {
            UserRegistrationResponse response = userService.completeRegistration(verificationCode);
            return new ResponseEntity<>(new UserApiResponse(true, response), ACCEPTED);
        } catch (TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest){
        try{
            userService.loginUser(userLoginRequest);
            return new ResponseEntity<>(new UserApiResponse(true,"Login Successful"), ACCEPTED);
        } catch(TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/logout/{id}")
    public ResponseEntity<?> logout(@PathVariable("id") int id){
        try{
            userService.logoutUser(id);
            return new ResponseEntity<>(new UserApiResponse(true, "Logout Successful"), ACCEPTED);
        } catch(TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest userUpdateRequest){
        try{
            UserUpdateResponse userUpdateResponse = userService.updateUser(userUpdateRequest);
            return new ResponseEntity<>(new UserApiResponse(true, userUpdateResponse), OK);
        } catch (TodoApplicationException e) {
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id){
        try{
            userService.deleteUser(id);
            return new ResponseEntity<>(new UserApiResponse(true, STR.process(StringTemplate.of("User Deleted Successfully"))), ACCEPTED);
        } catch (TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }






    @PostMapping("/createTask")
    public ResponseEntity<?> createTask(@RequestBody UserTaskCreationRequest userRegistrationRequest) {
        try{
            TaskCreationResponse response = userService.createTask(userRegistrationRequest);
            return new ResponseEntity<>(new UserApiResponse(true, response), CREATED);
        }
        catch(TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }


    @GetMapping("/getAllTask/{id}")
    public ResponseEntity<?> getAllTask(@PathVariable("id") int id){
        try{
            List<Task> tasks = userService.getAllTasks(id);
            return new ResponseEntity<>(new UserApiResponse(true, tasks), ACCEPTED);
        } catch (TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/assignTaskToNewUser")
    public ResponseEntity<?> assignTaskToNewUser(@RequestBody TaskAssignmentRequest taskAssignmentRequest){
        try{
            AssignTaskToNewUserResponse response = userService.assignTaskToNewUser(taskAssignmentRequest);
            return new ResponseEntity<>(new UserApiResponse(true, response), CREATED);
        } catch(TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
}
