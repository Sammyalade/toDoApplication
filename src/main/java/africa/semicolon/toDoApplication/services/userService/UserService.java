package africa.semicolon.toDoApplication.services.userService;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.User;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.dtos.response.TaskCreationResponse;
import africa.semicolon.toDoApplication.dtos.response.UserRegistrationResponse;

import java.util.List;

public interface UserService {

    UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest);
    TaskCreationResponse createTask(UserTaskCreationRequest userTaskCreationRequest);
    User searchUserById(int userId);
    void loginUser(UserLoginRequest userLoginRequest);
    void logoutUser(int id);
    void updateUser(UserUpdateRequest userUpdateRequest);
    void deleteUser(int id);
    void updateTask(UserTaskUpdateRequest taskUpdateRequest);
    List<Task> getAllTasks(int userId);
}
