package africa.semicolon.toDoApplication.contollers;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.dtos.response.*;
import africa.semicolon.toDoApplication.exception.TodoApplicationException;
import africa.semicolon.toDoApplication.services.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/toDoApplication")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        try{
            UserRegistrationResponse response = userService.registerUser(userRegistrationRequest);
            return new ResponseEntity<>(new UserApiResponse(true, response), CREATED);
        }
        catch(TodoApplicationException e){
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

    @PostMapping("/updateTask")
    public ResponseEntity<?> updateTask(@RequestBody UserTaskUpdateRequest userTaskUpdateRequest) {
        try{
            UserTaskUpdateResponse userTaskUpdateResponse = userService.updateTask(userTaskUpdateRequest);
            return new ResponseEntity<>(new UserApiResponse(true, userTaskUpdateResponse), CREATED);
        } catch (TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest){
        try{
            userService.loginUser(userLoginRequest);
            return new ResponseEntity<>(new UserApiResponse(true, STR.process(StringTemplate.of("Login Successful"))), ACCEPTED);
        } catch(TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/logout/{id}")
    public ResponseEntity<?> logout(@PathVariable("id") int id){
        try{
            userService.logoutUser(id);
            return new ResponseEntity<>(new UserApiResponse(true, STR.process(StringTemplate.of("Logout Successful"))), ACCEPTED);
        } catch(TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest userUpdateRequest){
        try{
            UserUpdateResponse userUpdateResponse = userService.updateUser(userUpdateRequest);
            return new ResponseEntity<>(new UserApiResponse(true, userUpdateResponse), CREATED);
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

    @GetMapping("/getAllTask/{id}")
    public ResponseEntity<?> getAllTask(@PathVariable("id") int id){
        try{
            List<Task> tasks = userService.getAllTasks(id);
            return new ResponseEntity<>(new UserApiResponse(true, tasks), ACCEPTED);
        } catch (TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
}
