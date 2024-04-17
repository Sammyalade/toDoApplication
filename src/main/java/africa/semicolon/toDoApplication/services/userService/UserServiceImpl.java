package africa.semicolon.toDoApplication.services.userService;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.User;
import africa.semicolon.toDoApplication.datas.repositories.UserRepository;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.dtos.response.TaskCreationResponse;
import africa.semicolon.toDoApplication.dtos.response.UserRegistrationResponse;
import africa.semicolon.toDoApplication.dtos.response.UserTaskUpdateResponse;
import africa.semicolon.toDoApplication.dtos.response.UserUpdateResponse;
import africa.semicolon.toDoApplication.exception.*;
import africa.semicolon.toDoApplication.services.EmailService;
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
    @Autowired
    private EmailService emailService;
    private UserRegistrationRequest userRegistrationRequest;

    @Override
    public void startRegistration(UserRegistrationRequest userRegistrationRequest) {
        this.userRegistrationRequest = userRegistrationRequest;
        checkEmptyUsername(userRegistrationRequest.getUsername());
        checkIfEmailExist(userRegistrationRequest.getEmail());

        emailService.sendVerificationEmail(userRegistrationRequest.getEmail(), userRegistrationRequest.getUsername());
    }
    @Override
    public UserRegistrationResponse completeRegistration(String verificationCode){
        if(verificationCode.equals(emailService.getVerificationCode())) {
            User user = map(userRegistrationRequest);
            taskListService.save(user.getTaskList());
            userRepository.save(user);
            return map(user);
        } else {
            throw new VerificationFailedException("Incorrect Verification Code. Please enter a correct code and try again");
        }
    }

    @Override
    public void sendNotification(Task task) {
        String message = """
                Dear %s,
                                
                This is to inform you that the following task is due:
                                
                Task Name: %s
                Due Date: %s
                                
                Please take necessary action.
                                
                Best regards,
                Your ToDo Application Team
                """;

        List<User> users = userRepository.findAll();
        for (User user : users) {
            if(user.getTaskList().getTasks().contains(task)) {
                String formattedString = String.format(message, user.getUsername(), task.getTitle(), task.getNotification().getTime());
                emailService.sendTaskNotificationEmail(user.getEmail(), formattedString);
            }
        }
    }


    private void checkIfEmailExist(String email) {
        if (userRepository.findByEmail(email) != null) {
            throw new EmailAlreadyRegisteredException("Email already registered. Please login instead");
        }
    }

    private static void checkEmptyUsername(String username) {
        if (isEmptyOrNullString(username)) {
            throw new EmptyStringException("Username cannot be null or empty");
        }
    }

    @Override
    public TaskCreationResponse createTask(UserTaskCreationRequest userTaskCreationRequest) {
        User user = searchUserById(userTaskCreationRequest.getUserId());
        if(!user.isLocked()) {
            TaskCreationRequest taskCreationRequest = map(userTaskCreationRequest);
            Task task = taskService.createTask(taskCreationRequest);
            taskListService.addTaskToTaskList(map(user.getTaskList().getId(), task.getId()));
            emailService.sendTaskCreationEmail(user.getEmail(), user.getUsername(), task.getTitle(), task.getNotification().getTime());
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
            if(user.getEmail().equals(userLoginRequest.getEmail()))
                user.setLocked(false);
            throw new UserNotFoundException("User not found");
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
        } else throw new UserNotLoggedInException("User not logged in. Please login and try again");
    }

    @Override
    public UserTaskUpdateResponse updateTask(UserTaskUpdateRequest userTaskUpdateRequest) {
        User user = searchUserById(userTaskUpdateRequest.getUserId());
        if(!user.isLocked()) {
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
