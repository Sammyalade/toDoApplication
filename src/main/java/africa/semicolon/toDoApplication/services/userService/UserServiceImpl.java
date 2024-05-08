package africa.semicolon.toDoApplication.services.userService;

import africa.semicolon.toDoApplication.datas.models.Priority;
import africa.semicolon.toDoApplication.datas.models.Status;
import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.User;
import africa.semicolon.toDoApplication.datas.repositories.UserRepository;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.dtos.response.*;
import africa.semicolon.toDoApplication.exceptions.*;
import africa.semicolon.toDoApplication.services.EmailService;
import africa.semicolon.toDoApplication.services.taskList.TaskListService;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static africa.semicolon.toDoApplication.datas.models.Priority.*;
import static africa.semicolon.toDoApplication.datas.models.Status.*;
import static africa.semicolon.toDoApplication.utility.EmailMessage.sendNotificationMessage;
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
        }
        throw new VerificationFailedException("Incorrect Verification Code. Please enter a correct code and try again");
    }

    @Override
    public UserUpdateResponse updateUser(UserUpdateRequest userUpdateRequest) {
        User user = searchUserById(userUpdateRequest.getId());
        if(!user.isLocked()) {
            if(userUpdateRequest.getUsername() != null) user.setUsername(userUpdateRequest.getUsername());
            if(!userUpdateRequest.getEmail().equals(user.getEmail())) {
                throw new TodoApplicationException("Email not found");
            }
            userRepository.save(user);
            return map(userUpdateRequest);
        }
        throw new UserNotLoggedInException("User not logged in. Please login and try again");
    }

    @Override
    public void loginUser(UserLoginRequest userLoginRequest) {
        User user = searchUserById(userLoginRequest.getId());
        if(user.getEmail().equals(userLoginRequest.getEmail()))
            user.setLocked(false);
        else throw new UserNotFoundException("User not found");
    }

    @Override
    public void logoutUser(int id) {
        User user = searchUserById(id);
        user.setLocked(true);
    }

    @Override
    public void deleteUser(int id) {
        User user = searchUserById(id);
        if(!user.isLocked()) {
            userRepository.delete(user);
        } else throw new UserNotLoggedInException("User not logged in. Please login and try again");
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
    public TaskCreationResponse createTask(UserTaskCreationRequest userTaskCreationRequest) {
        User user = searchUserById(userTaskCreationRequest.getUserId());
        if(!user.isLocked()) {
            TaskCreationRequest taskCreationRequest = map(userTaskCreationRequest);
            Task task = taskService.createTask(taskCreationRequest);
            taskListService.addTaskToTaskList(map(user.getTaskList().getId(), task.getId()));
            emailService.sendTaskCreationEmail(user.getEmail(), user.getUsername(), task.getTitle(),
                    task.getNotification().getTime());
            return map(task.getId(), task.getTitle(), task.getNotification().getId());
        }
        throw new UserNotLoggedInException("User not logged in. Please login and try again");
    }

    @Override
    public void updateTaskTitle(UserTaskTitleUpdateRequest userTaskTitleUpdateRequest) {
        User user = searchUserById(userTaskTitleUpdateRequest.getUserId());
        Task task = taskService.searchForTaskById(userTaskTitleUpdateRequest.getTaskId());
        if(user.getTaskList().getTasks().contains(task)){
            taskService.updateTaskTitle(map(userTaskTitleUpdateRequest));
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }



    @Override
    public void updateTaskDescription(UserTaskDescriptionUpdateRequest userTaskDescriptionUpdateRequest) {
        User user = searchUserById(userTaskDescriptionUpdateRequest.getUserId());
        Task task = taskService.searchForTaskById(userTaskDescriptionUpdateRequest.getTaskId());
        if(user.getTaskList().getTasks().contains(task)){
            taskService.updateTaskDescription(map(userTaskDescriptionUpdateRequest));
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }


    @Override
    public void updateTaskPriority(UserTaskPriorityUpdateRequest userTaskPriorityUpdateRequest) {
        User user = searchUserById(userTaskPriorityUpdateRequest.getUserId());
        Task task = taskService.searchForTaskById(userTaskPriorityUpdateRequest.getTaskId());
        if(user.getTaskList().getTasks().contains(task)){
            taskService.updateTaskPriority(map(userTaskPriorityUpdateRequest));
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }

    @Override
    public void updateTaskDueDate(UserTaskDueDateUpdateRequest userTaskDueDateUpdateRequest) {
        User user = searchUserById(userTaskDueDateUpdateRequest.getUserId());
        Task task = taskService.searchForTaskById(userTaskDueDateUpdateRequest.getTaskId());
        if(user.getTaskList().getTasks().contains(task)){
            taskService.updateTaskDueDate(map(userTaskDueDateUpdateRequest));
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }



    @Override
    public void updateTaskStatus(UserTaskStatusUpdateRequest userTaskStatusUpdateRequest) {
        User user = searchUserById(userTaskStatusUpdateRequest.getUserId());
        Task task = taskService.searchForTaskById(userTaskStatusUpdateRequest.getTaskId());
        if(user.getTaskList().getTasks().contains(task)){
            taskService.updateTaskStatus(map(userTaskStatusUpdateRequest));
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }



    @Override
    public void updateTaskNotificationTime(UserNotificationTimeUpdateRequest userNotificationTimeUpdateRequest) {
        User user = searchUserById(userNotificationTimeUpdateRequest.getUserId());
        Task task = taskService.searchForTaskById(userNotificationTimeUpdateRequest.getTaskId());
        if(user.getTaskList().getTasks().contains(task)){
            taskService.updateTaskNotificationTime(map(userNotificationTimeUpdateRequest));
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }

    @Override
    public List<Task> searchTaskByStatus(SearchByStatusRequest searchByStatusRequest) {
        List<Task> tasks = new ArrayList<>();
        User user = searchUserById(searchByStatusRequest.getUserId());
        Status status = checkTaskStatus(searchByStatusRequest.getStatusNumber());
        for(Task task: user.getTaskList().getTasks()){
            if(task.getStatus() == status){
                tasks.add(task);
            }
        }
        if(tasks.isEmpty()) throw new TaskNotFoundException("Task not found");
        return tasks;
    }

    @Override
    public List<Task> searchTaskByPriority(SearchByPriorityRequest searchByPriorityRequest) {
        List<Task> tasks = new ArrayList<>();
        User user = searchUserById(searchByPriorityRequest.getUserId());
        Priority priority = checkPriority(searchByPriorityRequest.getPriorityNumber());
        for(Task task: user.getTaskList().getTasks()){
            if(task.getPriority() == priority){
                tasks.add(task);
            }
        }
        if (tasks.isEmpty()) throw new TaskNotFoundException("Task not found");
        return tasks;
    }

    @Override
    public List<Task> searchTaskByTitle(SearchByTitleRequest searchByTitleRequest) {
        List<Task> foundTasks = new ArrayList<>();
        User user = searchUserById(searchByTitleRequest.getUserId());
        for(Task task: user.getTaskList().getTasks()){
            if (calculateSimilarity(task.getTitle().toLowerCase(),
                    searchByTitleRequest.getTitle().toLowerCase()) <= 2) {
                foundTasks.add(task);
            }
        }
        if(foundTasks.isEmpty()) throw new TaskNotFoundException("Task not found");
        return foundTasks;
    }

    @Override
    public List<Task> searchTaskByDueDate(SearchByDueDateRequest searchByDueDateRequest) {
        List<Task> foundTasks = new ArrayList<>();
        User user = searchUserById(searchByDueDateRequest.getUserId());
        for(Task task: user.getTaskList().getTasks()){
            if (task.getDueDate().equals(searchByDueDateRequest.getDueDate())) {
                foundTasks.add(task);
            }
        }
        if(foundTasks.isEmpty()) throw new TaskNotFoundException("Task not found");
        return foundTasks;
    }

    @Override
    public void deleteTask(TaskDeleteRequest taskDeleteRequest) {
        User user = searchUserById(taskDeleteRequest.getUserId());
        if(user.getTaskList().getTasks().contains(taskService.searchForTaskById(taskDeleteRequest.getTaskId()))){
            user.getTaskList().getTasks().remove(taskService.searchForTaskById(taskDeleteRequest.getTaskId()));
            taskService.deleteTask(taskDeleteRequest.getTaskId());
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }


    @Override
    public List<Task> getAllTasks(int userId) {
        if(!searchUserById(userId).isLocked()) {
            List<Task> tasks = taskListService.findAllTask(searchUserById(userId).getTaskList().getId());
            if(tasks.isEmpty()) throw new TodoApplicationException("Task is Empty");
            return tasks;
        }
        throw new UserNotLoggedInException("User not logged in. Please login and try again");
    }

    @Override
    public AssignTaskToNewUserResponse assignTaskToNewUser(TaskAssignmentRequest taskAssignment) {
        User assignor = userRepository.findByEmail(taskAssignment.getAssignorEmail());
        if(assignor != null) {
            User user = userRepository.findByEmail(taskAssignment.getAssigneeEmail());
            if (user == null) {
                User newUser = new User();
                newUser.setEmail(taskAssignment.getAssigneeEmail());
                newUser.setUsername(taskAssignment.getAssigneeUsername());
                userRepository.save(newUser);

                Task task = taskService.createTask(map(taskAssignment));
                newUser.setTaskList(taskListService.createTaskList());


                AddTaskToTaskListRequest addTaskToTaskListRequest = new AddTaskToTaskListRequest();
                addTaskToTaskListRequest.setTaskId(task.getId());
                addTaskToTaskListRequest.setTaskListId(newUser.getTaskList().getId());
                taskListService.addTaskToTaskList(addTaskToTaskListRequest);

                emailService.sendTaskCreatedEmailToNewUser(taskAssignment.getAssignorUsername(),
                        taskAssignment.getAssigneeUsername(),
                        taskAssignment.getAssigneeEmail());

                emailService.sendYouAssignedTaskEmail(taskAssignment.getAssignorUsername(),
                        taskAssignment.getAssigneeUsername(), taskAssignment.getAssignorEmail(),
                        taskAssignment.getTitle(), LocalDateTime.of(taskAssignment.getDueDate(),
                                taskAssignment.getNotificationTime()));

                return map(taskAssignment.getAssignorUsername(),
                        taskAssignment.getAssigneeUsername(),
                        taskAssignment.getTitle(),
                        task.getId(),
                        newUser.getId());
            }

            throw new EmailAlreadyRegisteredException("Email already registered, Please assign task to existing user");
        }
        throw new UserNotFoundException("Email not registered");
    }

    @Override
    public TaskAcceptanceResponse acceptTaskNewUser(TaskAcceptanceRequest taskAcceptanceRequest){
        User user = userRepository.findByEmail(taskAcceptanceRequest.getEmail());
        if(user != null){
            user.setUsername(taskAcceptanceRequest.getUsername());
            userRepository.save(user);
            TaskAcceptanceResponse response = new TaskAcceptanceResponse();
            response.setUserId(user.getId());
            return response;
        }
        throw new UserNotFoundException("Email not available");
    }

    @Override
    public AssignTaskToOldUserResponse assignTaskToOldUser(AssignTaskToOldUserRequest assignTaskToOldUserRequest){
        User assignor = userRepository.findByEmail(assignTaskToOldUserRequest.getAssignorEmail());
        if(assignor != null){
            User user = userRepository.findByEmail(assignTaskToOldUserRequest.getAssigneeEmail());
            if (user != null) {
                Task task = taskService.createTask(map(assignTaskToOldUserRequest));

                AddTaskToTaskListRequest addTaskToTaskListRequest = new AddTaskToTaskListRequest();
                addTaskToTaskListRequest.setTaskId(task.getId());
                addTaskToTaskListRequest.setTaskListId(user.getTaskList().getId());
                taskListService.addTaskToTaskList(addTaskToTaskListRequest);

                emailService.sendYouAssignedTaskEmail(assignTaskToOldUserRequest.getAssignorUsername(),
                        user.getUsername(), assignTaskToOldUserRequest.getAssignorEmail(),
                        assignTaskToOldUserRequest.getTitle(), LocalDateTime.of(assignTaskToOldUserRequest.getDueDate(),
                                assignTaskToOldUserRequest.getNotificationTime()));

                emailService.sendTaskAssignmentEmailForOldUser(user.getEmail(), user.getUsername(),
                        task.getNotification().getTime(), task.getTitle());

                return map(assignor.getId(), task.getTitle(), user.getUsername(), assignor.getUsername());
            }
            throw new UserNotFoundException("This user is not registered, Please look to assign task to new user");
        }
        throw new UserNotFoundException("Email not registered");
    }




    @Override
    public void sendNotification(Task task) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if(user.getTaskList().getTasks().contains(task)) {
                String formattedString = sendNotificationMessage(user.getUsername(), task.getTitle(),
                        task.getNotification().getTime());
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

    private int calculateSimilarity(String title1, String title2) {
        return LevenshteinDistance.getDefaultInstance().apply(title1, title2);
    }

    private Status checkTaskStatus(int statusNumber){
        Status status;
        switch(statusNumber){
            case 1 -> status = CREATED;
            case 2 -> status = IN_PROGRESS;
            case 3 -> status = COMPLETED;
            case 4 -> status = ON_HOLD;
            case 5 -> status = CANCELLED;
            default -> throw new NumberNotFoundException("""
                    Task Status number is between 1 to 5
                    1-> CREATED
                    2-> IN_PROGRESS
                    3-> COMPLETED
                    4-> ON_HOLD
                    5-> CANCELLED
                    """);
        }
        return status;
    }

    private Priority checkPriority(int priorityNumber){
        Priority priority;
        switch(priorityNumber){
            case 1 -> priority = NO_PRIORITY;
            case 2 -> priority = LOW_PRIORITY;
            case 3 -> priority = MEDIUM_PRIORITY;
            case 4 -> priority = HIGH_PRIORITY;
            default -> throw new NumberNotFoundException("""
                    Task Priority number is between 1 to 4
                    1-> NO_PRIORITY
                    2-> LOW_PRIORITY
                    3-> MEDIUM_PRIORITY
                    4-> HIGH_PRIORITY
                    """);
        }
        return priority;
    }

    @Override
    public void save(User user){
        userRepository.save(user);
    }
}
