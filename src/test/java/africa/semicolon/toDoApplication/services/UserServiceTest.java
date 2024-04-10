package africa.semicolon.toDoApplication.services;

import africa.semicolon.toDoApplication.datas.repositories.UserRepository;
import africa.semicolon.toDoApplication.dtos.request.UserRegistrationRequest;
import africa.semicolon.toDoApplication.dtos.response.UserRegistrationResponse;
import africa.semicolon.toDoApplication.services.notificationService.NotificationService;
import africa.semicolon.toDoApplication.services.taskList.TaskListService;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
import africa.semicolon.toDoApplication.services.userService.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskService taskService;
    @Autowired
    private NotificationService notificationService;
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
        //userService.createTask(userTaskCreationRequest);

    }
}
