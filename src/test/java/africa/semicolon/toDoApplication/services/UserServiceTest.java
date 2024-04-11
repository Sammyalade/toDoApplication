package africa.semicolon.toDoApplication.services;

import africa.semicolon.toDoApplication.datas.models.Priority;
import africa.semicolon.toDoApplication.datas.models.Status;
import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.TaskList;
import africa.semicolon.toDoApplication.datas.repositories.UserRepository;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.dtos.response.TaskCreationResponse;
import africa.semicolon.toDoApplication.dtos.response.UserRegistrationResponse;
import africa.semicolon.toDoApplication.exception.EmailAlreadyRegisteredException;
import africa.semicolon.toDoApplication.exception.EmptyStringException;
import africa.semicolon.toDoApplication.exception.UserNotFoundException;
import africa.semicolon.toDoApplication.exception.UserNotLoggedInException;
import africa.semicolon.toDoApplication.services.taskList.TaskListService;
import africa.semicolon.toDoApplication.services.userService.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskListService taskListService;


    @Test
    public void testThatUserCanRegister() {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUsername("username");
        userRegistrationRequest.setEmail("email@email.com");
        userService.registerUser(userRegistrationRequest);
        assertThat(userRepository.count(), is(1L));
    }

    @Test
    public void registerUser_createTask_taskIsCreated_taskListIsCreatedTest() {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUsername("username");
        userRegistrationRequest.setEmail("email@email.com");
        UserRegistrationResponse user = userService.registerUser(userRegistrationRequest);
        UserTaskCreationRequest userTaskCreationRequest = new UserTaskCreationRequest();
        userTaskCreationRequest.setUserId(user.getUserId());
        userTaskCreationRequest.setTitle("Title");
        userTaskCreationRequest.setDescription("Description");
        userTaskCreationRequest.setNotificationMessage("Message");
        userTaskCreationRequest.setDueDate(LocalDate.now());
        userTaskCreationRequest.setNotificationTime(LocalTime.now());
        userService.createTask(userTaskCreationRequest);
        assertThat(userRepository.count(), is(1L));
        TaskList taskList = taskListService.searchForTaskList(user.getTaskListId());
        assertThat(taskList.getTasks().size(),is(1));
    }

    @Test
    public void registerUserWithNullUsername_throwsNullException() {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUsername(null);
        userRegistrationRequest.setEmail("email@email.com");
        assertThatThrownBy(()->{
            userService.registerUser(userRegistrationRequest);
        })
                .isInstanceOf(EmptyStringException.class)
                .hasMessageContaining("Username cannot be null or empty");

    }

    @Test
    public void registerUserWithEmptyStringUsername_throwsNullException() {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUsername("");
        userRegistrationRequest.setEmail("email@email.com");
        assertThatThrownBy(()->{
            userService.registerUser(userRegistrationRequest);
        })
                .isInstanceOf(EmptyStringException.class)
                .hasMessageContaining("Username cannot be null or empty");

    }

    @Test
    public void registerUser_createTask_updateTask_taskIsUpdatedTest() {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUsername("username");
        userRegistrationRequest.setEmail("email@email.com");
        UserRegistrationResponse user = userService.registerUser(userRegistrationRequest);
        UserTaskCreationRequest userTaskCreationRequest = new UserTaskCreationRequest();
        userTaskCreationRequest.setUserId(user.getUserId());
        userTaskCreationRequest.setTitle("Title");
        userTaskCreationRequest.setDescription("Description");
        userTaskCreationRequest.setNotificationMessage("Message");
        userTaskCreationRequest.setDueDate(LocalDate.now());
        userTaskCreationRequest.setNotificationTime(LocalTime.now());
        TaskCreationResponse taskCreationResponse = userService.createTask(userTaskCreationRequest);
        UserTaskUpdateRequest taskUpdateRequest = new UserTaskUpdateRequest();
        taskUpdateRequest.setUserId(user.getUserId());
        taskUpdateRequest.setTaskId(taskCreationResponse.getTaskId());
        taskUpdateRequest.setTitle("New Title");
        taskUpdateRequest.setDescription("New Description");
        taskUpdateRequest.setRead(true);
        taskUpdateRequest.setDueDate(LocalDate.now());
        taskUpdateRequest.setMessage("New Message");
        taskUpdateRequest.setPriority(Priority.HIGH_PRIORITY);
        taskUpdateRequest.setStatus(Status.IN_PROGRESS);
        taskUpdateRequest.setTime(LocalTime.now());
        taskUpdateRequest.setNotificationId(taskCreationResponse.getNotificationId());
        userService.updateTask(taskUpdateRequest);
        Task task = taskListService.searchForTaskList(user.getTaskListId()).getTasks().get(0);
        assertThat(task.getTitle(), is("New Title"));
        assertThat(task.getStatus(), is(Status.IN_PROGRESS));
        assertThat(task.getDueDate(), is(LocalDate.now()));
        assertThat(task.getPriority(), is(Priority.HIGH_PRIORITY));
    }

    @Test
    public void searchForUserThatDoesNotExist_throwsException() {
        assertThatThrownBy(()-> {
            userService.searchUserById(11);
        })
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    public void registerUser_loginUser_userIsLoggedInTest(){
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUsername("username");
        userRegistrationRequest.setEmail("email@email.com");
        UserRegistrationResponse user = userService.registerUser(userRegistrationRequest);
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setId(user.getUserId());
        userLoginRequest.setEmail(user.getEmail());
        userService.loginUser(userLoginRequest);
        assertThat(userService.searchUserById(user.getUserId()).isLocked(), is(false));
    }

    @Test
    public void registerUser_loginUser_logoutUser_userIsLoggedOutTest(){
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUsername("username");
        userRegistrationRequest.setEmail("email@email.com");
        UserRegistrationResponse user = userService.registerUser(userRegistrationRequest);
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setId(user.getUserId());
        userLoginRequest.setEmail(user.getEmail());
        userService.loginUser(userLoginRequest);
        userService.logoutUser(user.getUserId());
        assertThat(userService.searchUserById(user.getUserId()).isLocked(), is(true));
    }

    @Test
    public void registerUser_loginUser_logoutUser_loginUser_userIsLoggedInTest(){
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUsername("username");
        userRegistrationRequest.setEmail("email@email.com");
        UserRegistrationResponse user = userService.registerUser(userRegistrationRequest);
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setId(user.getUserId());
        userLoginRequest.setEmail(user.getEmail());
        userService.loginUser(userLoginRequest);
        userService.logoutUser(user.getUserId());
        userService.loginUser(userLoginRequest);
        assertThat(userService.searchUserById(user.getUserId()).isLocked(), is(false));
    }

    @Test
    public void createUser_updateUser_userIsUpdatedTest(){
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUsername("username");
        userRegistrationRequest.setEmail("email@email.com");
        UserRegistrationResponse user = userService.registerUser(userRegistrationRequest);
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(user.getUserId());
        userUpdateRequest.setEmail("myEmail@email.com");
        userUpdateRequest.setUsername("NewUsername");
        userService.updateUser(userUpdateRequest);
        assertThat(userService.searchUserById(user.getUserId()).getEmail(), is("myEmail@email.com"));
        assertThat(userService.searchUserById(user.getUserId()).getUsername(), is("NewUsername"));
    }

    @Test
    public void registerUser_loginUser_logOutUser_updateUser_throwsExceptionTest(){
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUsername("username");
        userRegistrationRequest.setEmail("email@email.com");
        UserRegistrationResponse user = userService.registerUser(userRegistrationRequest);
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setId(user.getUserId());
        userLoginRequest.setEmail(user.getEmail());
        userService.loginUser(userLoginRequest);
        userService.logoutUser(user.getUserId());
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(user.getUserId());
        userUpdateRequest.setEmail("myEmail@email.com");
        userUpdateRequest.setUsername("NewUsername");
        assertThatThrownBy(()->{
            userService.updateUser(userUpdateRequest);
        })
                .isInstanceOf(UserNotLoggedInException.class)
                .hasMessageContaining("User not logged in. Please login and try again");
    }

    @Test
    public void registerUser_logoutUser_createTask_throwsExceptionTest(){
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUsername("username");
        userRegistrationRequest.setEmail("email@email.com");
        UserRegistrationResponse user = userService.registerUser(userRegistrationRequest);
        userService.logoutUser(user.getUserId());
        UserTaskCreationRequest userTaskCreationRequest = new UserTaskCreationRequest();
        assertThatThrownBy(()->{
            userService.createTask(userTaskCreationRequest);
        })
                .isInstanceOf(UserNotLoggedInException.class)
                .hasMessageContaining("User not logged in. Please login and try again");
    }

    @Test
    public void registerUser_logoutUser_deleteUser_throwsExceptionTest(){
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUsername("username");
        userRegistrationRequest.setEmail("email@email.com");
        UserRegistrationResponse user = userService.registerUser(userRegistrationRequest);
        userService.logoutUser(user.getUserId());
        assertThatThrownBy(()->{
            userService.deleteUser(user.getUserId());
        })
                .isInstanceOf(UserNotLoggedInException.class)
                .hasMessageContaining("User not logged in. Please login and try again");
    }

    @Test
    public void createUserWithTheSameEmail_exceptionIsThrownTest(){
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        UserRegistrationRequest userRegistrationRequest2 = new UserRegistrationRequest();
        userRegistrationRequest.setUsername("username");
        userRegistrationRequest.setEmail("email@email.com");
        userRegistrationRequest2.setEmail("email@email.com");
        userRegistrationRequest2.setUsername("NewUsername");
        UserRegistrationResponse user = userService.registerUser(userRegistrationRequest);
        assertThatThrownBy(()->{
            userService.registerUser(userRegistrationRequest2);
        })
                .isInstanceOf(EmailAlreadyRegisteredException.class)
                .hasMessageContaining("Email already registered. Please login instead");
    }

    @Test
    public void testFindAll(){
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUsername("username");
        userRegistrationRequest.setEmail("email@email.com");
        UserRegistrationResponse user = userService.registerUser(userRegistrationRequest);
        UserTaskCreationRequest userTaskCreationRequest = new UserTaskCreationRequest();
        userTaskCreationRequest.setUserId(user.getUserId());
        userTaskCreationRequest.setTitle("Title");
        userTaskCreationRequest.setDescription("Description");
        userTaskCreationRequest.setNotificationMessage("Message");
        userTaskCreationRequest.setDueDate(LocalDate.now());
        userTaskCreationRequest.setNotificationTime(LocalTime.now());
        userService.createTask(userTaskCreationRequest);
        List<Task> tasks = userService.getAllTasks(user.getUserId());
        assertThat(userService.getAllTasks(user.getUserId()), is(tasks));
    }

    @Test
    public void findAllWhenUserIsLoggedOut_exceptionIsThrownTest(){
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setUsername("username");
        userRegistrationRequest.setEmail("email@email.com");
        UserRegistrationResponse user = userService.registerUser(userRegistrationRequest);
        UserTaskCreationRequest userTaskCreationRequest = new UserTaskCreationRequest();
        userTaskCreationRequest.setUserId(user.getUserId());
        userTaskCreationRequest.setTitle("Title");
        userTaskCreationRequest.setDescription("Description");
        userTaskCreationRequest.setNotificationMessage("Message");
        userTaskCreationRequest.setDueDate(LocalDate.now());
        userTaskCreationRequest.setNotificationTime(LocalTime.now());
        userService.createTask(userTaskCreationRequest);
        userService.logoutUser(user.getUserId());
        assertThatThrownBy(()-> {
            userService.getAllTasks(user.getUserId());
        })
                .isInstanceOf(UserNotLoggedInException.class)
                .hasMessageContaining("User not logged in. Please login and try again");
    }
}
