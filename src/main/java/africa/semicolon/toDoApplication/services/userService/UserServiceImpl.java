package africa.semicolon.toDoApplication.services.userService;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.User;
import africa.semicolon.toDoApplication.datas.repositories.UserRepository;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.dtos.response.TaskCreationResponse;
import africa.semicolon.toDoApplication.dtos.response.UserRegistrationResponse;
import africa.semicolon.toDoApplication.services.taskList.TaskListService;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static africa.semicolon.toDoApplication.utility.Mapper.map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskListService taskListService;
    @Autowired
    private TaskService taskService;

    public UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest) {
        User user = map(userRegistrationRequest);
        taskListService.save(user.getTaskList());
        userRepository.save(user);
        return map(user);
    }

    @Override
    public TaskCreationResponse createTask(UserTaskCreationRequest userTaskCreationRequest) {
        User user = searchUserById(userTaskCreationRequest.getUserId());
        TaskCreationRequest taskCreationRequest = map(userTaskCreationRequest);
        Task task = taskService.createTask(taskCreationRequest);
        taskListService.addTaskToTaskList(map(user.getTaskList().getId(), task.getId()));
        TaskCreationResponse taskCreationResponse = new TaskCreationResponse();

    }



    @Override
    public User searchUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.get();
    }

    @Override
    public void loginUser(UserLoginRequest userLoginRequest) {
        Optional<User> user = userRepository.findById(userLoginRequest.getId());
        if (user.isPresent()) {
            User user1 = user.get();
            if(user1.getEmail().equals(userLoginRequest.getEmail())){
                user1.setLocked(false);
            }
        }

    }

    @Override
    public void logoutUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setLocked(true);
        }
    }

    @Override
    public void updateUser(UserUpdateRequest userUpdateRequest) {
        Optional<User> user = userRepository.findById(userUpdateRequest.getId());
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setEmail(userUpdateRequest.getEmail());
        }
    }

    @Override
    public void deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> userRepository.delete(value));
    }
}
