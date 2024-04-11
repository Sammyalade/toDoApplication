package africa.semicolon.toDoApplication.services.userService;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.User;
import africa.semicolon.toDoApplication.datas.repositories.UserRepository;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.dtos.response.TaskCreationResponse;
import africa.semicolon.toDoApplication.dtos.response.UserRegistrationResponse;
import africa.semicolon.toDoApplication.dtos.response.UserTaskUpdateResponse;
import africa.semicolon.toDoApplication.dtos.response.UserUpdateResponse;
import africa.semicolon.toDoApplication.exception.EmailAlreadyRegisteredException;
import africa.semicolon.toDoApplication.exception.EmptyStringException;
import africa.semicolon.toDoApplication.exception.UserNotFoundException;
import africa.semicolon.toDoApplication.exception.UserNotLoggedInException;
import africa.semicolon.toDoApplication.services.taskList.TaskListService;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static africa.semicolon.toDoApplication.utility.Mapper.map;
import static africa.semicolon.toDoApplication.utility.Utility.isEmptyOrNullString;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskListService taskListService;
    @Autowired
    private TaskService taskService;

    public UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest) {
        if (isEmptyOrNullString(userRegistrationRequest.getUsername())) throw new EmptyStringException("Username cannot be null or empty");
        if(userRepository.findByEmail(userRegistrationRequest.getEmail()) != null)
            throw new EmailAlreadyRegisteredException("Email already registered. Please login instead");
        User user = map(userRegistrationRequest);
        taskListService.save(user.getTaskList());
        userRepository.save(user);
        return map(user);
    }

    @Override
    public TaskCreationResponse createTask(UserTaskCreationRequest userTaskCreationRequest) {
        User user = searchUserById(userTaskCreationRequest.getUserId());
        if(!user.isLocked()) {
            TaskCreationRequest taskCreationRequest = map(userTaskCreationRequest);
            Task task = taskService.createTask(taskCreationRequest);
            taskListService.addTaskToTaskList(map(user.getTaskList().getId(), task.getId()));
            return map(task.getId(), task.getTitle(), task.getNotification().getId());
        }
        throw new UserNotLoggedInException("User not logged in. Please login and try again");
    }



    @Override
    public User searchUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserNotFoundException("User not found");
    }

    @Override
    public void loginUser(UserLoginRequest userLoginRequest) {
        User user = searchUserById(userLoginRequest.getId());
            if(user.getEmail().equals(userLoginRequest.getEmail())) user.setLocked(false);

    }

    @Override
    public void logoutUser(int id) {
        User user = searchUserById(id);
        user.setLocked(true);
    }

    @Override
    public UserUpdateResponse updateUser(UserUpdateRequest userUpdateRequest) {
        User user = searchUserById(userUpdateRequest.getId());
        if(!user.isLocked()) {
            if(userUpdateRequest.getUsername() != null) user.setUsername(userUpdateRequest.getUsername());
            if(userUpdateRequest.getEmail() != null) user.setEmail(userUpdateRequest.getEmail());
            userRepository.save(user);
            return map(userUpdateRequest);
        }
        throw new UserNotLoggedInException("User not logged in. Please login and try again");
    }



    @Override
    public void deleteUser(int id) {
       User user = searchUserById(id);
        if(!user.isLocked()) {
            userRepository.delete(user);
        }
        throw new UserNotLoggedInException("User not logged in. Please login and try again");
    }

    @Override
    public UserTaskUpdateResponse updateTask(UserTaskUpdateRequest userTaskUpdateRequest) {
        if(!searchUserById(userTaskUpdateRequest.getUserId()).isLocked()) {
            TaskUpdateRequest taskUpdateRequest = map(userTaskUpdateRequest);
            taskService.updateTask(taskUpdateRequest);
            userRepository.save(searchUserById(userTaskUpdateRequest.getUserId()));
            return map(taskUpdateRequest, userTaskUpdateRequest.getUserId());
        }
        throw new UserNotLoggedInException("User not logged in. Please login and try again");
    }

    @Override
    public List<Task> getAllTasks(int userId) {
        if(!searchUserById(userId).isLocked()) return taskListService.findAllTask(searchUserById(userId).getTaskList().getId());
        throw new UserNotLoggedInException("User not logged in. Please login and try again");
    }


}
