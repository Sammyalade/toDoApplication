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

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/toDoApplication")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/home")
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

    @PostMapping("/acceptTaskNewUser")
    public ResponseEntity<?> acceptTaskNewUser(@RequestBody TaskAcceptanceRequest taskAcceptanceRequest){
        try {
            TaskAcceptanceResponse taskAcceptanceResponse = userService.acceptTaskNewUser(taskAcceptanceRequest);
            return new ResponseEntity<>(new UserApiResponse(true, taskAcceptanceResponse), CREATED);
        } catch (TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/assignTaskToOldUser")
    public ResponseEntity<?> assignTaskToOldUser(@RequestBody AssignTaskToOldUserRequest assignTaskToOldUserRequest){
        try {
            AssignTaskToOldUserResponse response = userService.assignTaskToOldUser(assignTaskToOldUserRequest);
            return new ResponseEntity<>(new UserApiResponse(true, response), CREATED);
        } catch (TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/updateTaskDueDate")
    public ResponseEntity<?> updateTaskDueDate(@RequestBody UserTaskDueDateUpdateRequest userTaskDueDateUpdateRequest){
        try{
            userService.updateTaskDueDate(userTaskDueDateUpdateRequest);
            return new ResponseEntity<>(new UserApiResponse(true, "Update Task Due Date Successful"), OK);
        } catch(TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/updateTaskTitle")
    public ResponseEntity<?> updateTaskTitle(@RequestBody UserTaskTitleUpdateRequest userTaskTitleUpdateRequest){
        try{
            userService.updateTaskTitle(userTaskTitleUpdateRequest);
            return new ResponseEntity<>(new UserApiResponse(true, "Update Task Title Successful"), OK);
        } catch(TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/updateTaskDescription")
    public ResponseEntity<?> updateTaskDescription(@RequestBody UserTaskDescriptionUpdateRequest userTaskDescriptionUpdateRequest){
        try{
            userService.updateTaskDescription(userTaskDescriptionUpdateRequest);
            return new ResponseEntity<>(new UserApiResponse(true, "Update Task Description Successful"), OK);
        } catch(TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }


    @PostMapping("/updateTaskPriority")
    public ResponseEntity<?> updateTaskPriority(@RequestBody UserTaskPriorityUpdateRequest userTaskPriorityUpdateRequest){
        try{
            userService.updateTaskPriority(userTaskPriorityUpdateRequest);
            return new ResponseEntity<>(new UserApiResponse(true, "Update Task Priority Successful"), OK);
        } catch(TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/updateTaskStatus")
    public ResponseEntity<?> updateTaskStatus(@RequestBody UserTaskStatusUpdateRequest userTaskStatusUpdateRequest){
        try{
            userService.updateTaskStatus(userTaskStatusUpdateRequest);
            return new ResponseEntity<>(new UserApiResponse(true, "Update Task Status Successful"), OK);
        } catch(TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/updateTaskNotificationTime")
    public ResponseEntity<?> updateTaskNotificationTime(@RequestBody UserNotificationTimeUpdateRequest userNotificationTimeUpdateRequest){
        try{
            userService.updateTaskNotificationTime(userNotificationTimeUpdateRequest);
            return new ResponseEntity<>(new UserApiResponse(true, "Update Task Notification Time Successful"), OK);
        } catch(TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/searchTaskByStatus")
    public ResponseEntity<?> searchTaskByStatus(@RequestBody SearchByStatusRequest searchByStatusRequest){
        try {
            List<Task> tasks = userService.searchTaskByStatus(searchByStatusRequest);
            return new ResponseEntity<>(new UserApiResponse(true, tasks), OK);
        } catch (TodoApplicationException e) {
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }


    @GetMapping("/searchTaskByPriority")
    public ResponseEntity<?> searchTaskByPriority(@RequestBody SearchByPriorityRequest searchByPriorityRequest){
        try {
            List<Task> tasks = userService.searchTaskByPriority(searchByPriorityRequest);
            return new ResponseEntity<>(new UserApiResponse(true, tasks), OK);
        } catch (TodoApplicationException e) {
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }


    @GetMapping("/searchTaskByTitle")
    public ResponseEntity<?> searchTaskByTitle(@RequestBody SearchByTitleRequest searchByTitleRequest){
        try {
            List<Task> tasks = userService.searchTaskByTitle(searchByTitleRequest);
            return new ResponseEntity<>(new UserApiResponse(true, tasks), OK);
        } catch (TodoApplicationException e) {
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }


    @GetMapping("/searchTaskByDueDate")
    public ResponseEntity<?> searchTaskByDueDate(@RequestBody SearchByDueDateRequest searchByDueDateRequest){
        try {
            List<Task> tasks = userService.searchTaskByDueDate(searchByDueDateRequest);
            return new ResponseEntity<>(new UserApiResponse(true, tasks), OK);
        } catch (TodoApplicationException e) {
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/deleteTask")
    public ResponseEntity<?> deleteTask(@RequestBody TaskDeleteRequest taskDeleteRequest){
        try{
            userService.deleteTask(taskDeleteRequest);
            return new ResponseEntity<>(new UserApiResponse(true, "Delete Task Successful"), OK);
        } catch(TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }



}
