package africa.semicolon.toDoApplication.services.userService;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.User;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.dtos.response.*;

import java.util.List;

public interface UserService {

    void startRegistration(UserRegistrationRequest userRegistrationRequest);
    UserRegistrationResponse completeRegistration(String verificationCode);
    UserUpdateResponse updateUser(UserUpdateRequest userUpdateRequest);
    void loginUser(UserLoginRequest userLoginRequest);
    void logoutUser(int id);
    void deleteUser(int id);
    User searchUserById(int userId);


    TaskCreationResponse createTask(UserTaskCreationRequest userTaskCreationRequest);
    void updateTaskTitle(UserTaskTitleUpdateRequest userTaskTitleUpdateRequest);
    void updateTaskDescription(UserTaskDescriptionUpdateRequest userTaskDescriptionUpdateRequest);
    void updateTaskPriority(UserTaskPriorityUpdateRequest userTaskPriorityUpdateRequest);
    void updateTaskDueDate(UserTaskDueDateUpdateRequest userTaskDueDateUpdateRequest);
    void updateTaskStatus(UserTaskStatusUpdateRequest userTaskStatusUpdateRequest);
    void updateTaskNotificationTime(UserNotificationTimeUpdateRequest userNotificationTimeUpdateRequest);
    List<Task> searchTaskByStatus(SearchByStatusRequest searchByStatusRequest);
    List<Task> searchTaskByPriority(SearchByPriorityRequest searchByPriorityRequest);
    List<Task> searchTaskByTitle(SearchByTitleRequest searchByTitleRequest);
    List<Task> searchTaskByDueDate(SearchByDueDateRequest searchByDueDateRequest);
    void deleteTask(TaskDeleteRequest taskDeleteRequest);

    TaskAcceptanceResponse acceptTaskNewUser(TaskAcceptanceRequest taskAcceptanceRequest);

    AssignTaskToOldUserResponse assignTaskToOldUser(AssignTaskToOldUserRequest assignTaskToOldUserRequest);

    void sendNotification(Task task);
    List<Task> getAllTasks(int userId);
    AssignTaskToNewUserResponse assignTaskToNewUser(TaskAssignmentRequest taskAssignment);
}
