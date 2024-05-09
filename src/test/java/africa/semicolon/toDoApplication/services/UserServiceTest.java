package africa.semicolon.toDoApplication.services;

import africa.semicolon.toDoApplication.datas.models.Priority;
import africa.semicolon.toDoApplication.datas.models.Status;
import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.User;
import africa.semicolon.toDoApplication.datas.repositories.UserRepository;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.exceptions.*;
import africa.semicolon.toDoApplication.services.taskList.TaskListService;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskListService taskListService;
    @Autowired
    private TaskService taskService;

    @Test
    public void createUser_updateUser_userIsUpdatedTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        userService.save(newUser);
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUsername("newUsername");
        userUpdateRequest.setEmail("email");
        userUpdateRequest.setId(newUser.getId());
        userService.updateUser(userUpdateRequest);
        User user = userService.searchUserById(newUser.getId());
        assertThat(user.getUsername(), is("newUsername"));
    }

    @Test
    public void createUser_updateWithWrongEmail_exceptionIsThrownTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        userService.save(newUser);
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUsername("newUsername");
        userUpdateRequest.setEmail("email@");
        userUpdateRequest.setId(newUser.getId());
        assertThatThrownBy(()->{
            userService.updateUser(userUpdateRequest);
        })
                .isInstanceOf(TodoApplicationException.class)
                .hasMessageContaining("Email not found");
    }

    @Test
    public void createUser_login_userIsLoggedInTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(true);
        userService.save(newUser);
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail(newUser.getEmail());
        userLoginRequest.setId(newUser.getId());
        userService.loginUser(userLoginRequest);
        assertThat(userService.searchUserById(newUser.getId()).isLocked(), is(false));
    }

    @Test
    public void createUser_login_logout_userIsLoggedOutTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        userService.save(newUser);
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail(newUser.getEmail());
        userLoginRequest.setId(newUser.getId());
        userService.loginUser(userLoginRequest);
        userService.logoutUser(newUser.getId());
        assertThat(userService.searchUserById(newUser.getId()).isLocked(), is(true));
    }

    @Test
    public void createUser_lockUser_tryToUpdateUser_exceptionIsThrown(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(true);
        userService.save(newUser);
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUsername("newUsername");
        userUpdateRequest.setEmail("email");
        userUpdateRequest.setId(newUser.getId());
        assertThatThrownBy(()->{
            userService.updateUser(userUpdateRequest);
        })
                .isInstanceOf(UserNotLoggedInException.class)
                .hasMessageContaining("User not logged in. Please login and try again");
    }

    @Test
    public void createUser_updateUserNotInDatabase_exceptionIsThrownTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(true);
        userService.save(newUser);
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUsername("newUsername");
        userUpdateRequest.setEmail("email");
        userUpdateRequest.setId(11);
        assertThatThrownBy(()->{
            userService.updateUser(userUpdateRequest);
        })
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    public void createUser_deleteUser_userIsDeletedTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(false);
        userService.save(newUser);
        userService.deleteUser(newUser.getId());
        assertThatThrownBy(()->{
            userService.searchUserById(newUser.getId());
        })
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    public void createUser_createTaskForUser_taskIsCreatedTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(false);
        newUser.setTaskList(taskListService.createTaskList());
        userService.save(newUser);
        TaskCreationRequest request = new TaskCreationRequest();
        request.setTitle("Title");
        request.setDescription("Description");
        request.setDueDate(LocalDate.parse("2024-05-07"));
        request.setNotificationTime(LocalTime.parse("09:00"));
        request.setNotificationMessage("Message");
        Task task = taskService.createTask(request);
        newUser.getTaskList().getTasks().add(task);
        assertThat(userService.searchUserById(newUser.getId()).getTaskList().getTasks().size(), is(1));
    }

    @Test
    public void createUser_createTask_updateTaskTitle_taskTitleIsUpdatedTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(false);
        newUser.setTaskList(taskListService.createTaskList());
        userService.save(newUser);
        TaskCreationRequest request = new TaskCreationRequest();
        request.setTitle("Title");
        request.setDescription("Description");
        request.setDueDate(LocalDate.parse("2024-05-07"));
        request.setNotificationTime(LocalTime.parse("09:00"));
        request.setNotificationMessage("Message");
        Task task = taskService.createTask(request);
        newUser.getTaskList().getTasks().add(task);
        UserTaskTitleUpdateRequest request1 = new UserTaskTitleUpdateRequest();
        request1.setUserId(newUser.getId());
        request1.setTaskId(task.getId());
        request1.setTitle("My title");
        userService.updateTaskTitle(request1);
        assertThat(taskService.searchForTaskById(task.getId()).getTitle(), is("my title"));
    }

    @Test
    public void createUser_createTask_updateTaskWithEmptyString_exceptionISThrownTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(false);
        newUser.setTaskList(taskListService.createTaskList());
        userService.save(newUser);
        TaskCreationRequest request = new TaskCreationRequest();
        request.setTitle("Title");
        request.setDescription("Description");
        request.setDueDate(LocalDate.parse("2024-05-07"));
        request.setNotificationTime(LocalTime.parse("09:00"));
        request.setNotificationMessage("Message");
        Task task = taskService.createTask(request);
        newUser.getTaskList().getTasks().add(task);
        UserTaskTitleUpdateRequest request1 = new UserTaskTitleUpdateRequest();
        request1.setUserId(newUser.getId());
        request1.setTaskId(task.getId());
        request1.setTitle("");
        assertThatThrownBy(()->{
            userService.updateTaskTitle(request1);
        })
                .isInstanceOf(EmptyStringException.class)
                .hasMessageContaining("Task Title cannot be blank, or empty or null");
    }

    @Test
    public void createUser_createTask_updateTaskDescription_taskDescriptionIsUpdatedTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(false);
        newUser.setTaskList(taskListService.createTaskList());
        userService.save(newUser);
        TaskCreationRequest request = new TaskCreationRequest();
        request.setTitle("Title");
        request.setDescription("Description");
        request.setDueDate(LocalDate.parse("2024-05-07"));
        request.setNotificationTime(LocalTime.parse("09:00"));
        request.setNotificationMessage("Message");
        Task task = taskService.createTask(request);
        newUser.getTaskList().getTasks().add(task);
        UserTaskDescriptionUpdateRequest request1 = new UserTaskDescriptionUpdateRequest();
        request1.setUserId(newUser.getId());
        request1.setTaskId(task.getId());
        request1.setDescription("My description");
        userService.updateTaskDescription(request1);
        assertThat(taskService.searchForTaskById(task.getId()).getDescription(), is("my description"));
    }

    @Test
    public void createUser_createTask_updateTaskStatus_statusIsUpdatedTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(false);
        newUser.setTaskList(taskListService.createTaskList());
        userService.save(newUser);
        TaskCreationRequest request = new TaskCreationRequest();
        request.setTitle("Title");
        request.setDescription("Description");
        request.setDueDate(LocalDate.parse("2024-05-07"));
        request.setNotificationTime(LocalTime.parse("09:00"));
        request.setNotificationMessage("Message");
        Task task = taskService.createTask(request);
        newUser.getTaskList().getTasks().add(task);
        UserTaskStatusUpdateRequest request1 = new UserTaskStatusUpdateRequest();
        request1.setUserId(newUser.getId());
        request1.setTaskId(task.getId());
        request1.setStatusNumber(3);
        userService.updateTaskStatus(request1);
        assertThat(taskService.searchForTaskById(task.getId()).getStatus(), is(Status.COMPLETED));
    }


    @Test
    public void createUser_createTask_updateTaskPriority_priority_IsUpdatedTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(false);
        newUser.setTaskList(taskListService.createTaskList());
        userService.save(newUser);
        TaskCreationRequest request = new TaskCreationRequest();
        request.setTitle("Title");
        request.setDescription("Description");
        request.setDueDate(LocalDate.parse("2024-05-07"));
        request.setNotificationTime(LocalTime.parse("09:00"));
        request.setNotificationMessage("Message");
        Task task = taskService.createTask(request);
        newUser.getTaskList().getTasks().add(task);
        UserTaskPriorityUpdateRequest request1 = new UserTaskPriorityUpdateRequest();
        request1.setUserId(newUser.getId());
        request1.setTaskId(task.getId());
        request1.setPriorityNumber(3);
        userService.updateTaskPriority(request1);
        assertThat(taskService.searchForTaskById(task.getId()).getPriority(), is(Priority.MEDIUM_PRIORITY));
    }

    @Test
    public void createUser_createTask_updateTaskPriorityWithWrongNumber_exceptionIsThrownTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(false);
        newUser.setTaskList(taskListService.createTaskList());
        userService.save(newUser);
        TaskCreationRequest request = new TaskCreationRequest();
        request.setTitle("Title");
        request.setDescription("Description");
        request.setDueDate(LocalDate.parse("2024-05-07"));
        request.setNotificationTime(LocalTime.parse("09:00"));
        request.setNotificationMessage("Message");
        Task task = taskService.createTask(request);
        newUser.getTaskList().getTasks().add(task);
        UserTaskPriorityUpdateRequest request1 = new UserTaskPriorityUpdateRequest();
        request1.setUserId(newUser.getId());
        request1.setTaskId(task.getId());
        request1.setPriorityNumber(111);

        assertThatThrownBy(()->{
            userService.updateTaskPriority(request1);
        })
                .isInstanceOf(NumberNotFoundException.class)
                .hasMessageContaining("""
                    Task Priority number is between 1 to 4
                    1-> NO_PRIORITY
                    2-> LOW_PRIORITY
                    3-> MEDIUM_PRIORITY
                    4-> HIGH_PRIORITY
                    """);
    }

    @Test
    public void createUser_createTask_updateTaskStatusWithWrongNumber_exceptionIsThrownTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(false);
        newUser.setTaskList(taskListService.createTaskList());
        userService.save(newUser);
        TaskCreationRequest request = new TaskCreationRequest();
        request.setTitle("Title");
        request.setDescription("Description");
        request.setDueDate(LocalDate.parse("2024-05-07"));
        request.setNotificationTime(LocalTime.parse("09:00"));
        request.setNotificationMessage("Message");
        Task task = taskService.createTask(request);
        newUser.getTaskList().getTasks().add(task);
        UserTaskStatusUpdateRequest request1 = new UserTaskStatusUpdateRequest();
        request1.setUserId(newUser.getId());
        request1.setTaskId(task.getId());
        request1.setStatusNumber(111);
        assertThatThrownBy(()->{
            userService.updateTaskStatus(request1);
        })
                .isInstanceOf(NumberNotFoundException.class)
                .hasMessageContaining("""
                    Task Status number is between 1 to 5
                    1-> CREATED
                    2-> IN_PROGRESS
                    3-> COMPLETED
                    4-> ON_HOLD
                    5-> CANCELLED
                    """);

    }

    @Test
    public void createUser_createTask_updateTaskDueDate_dueDateIsUpdatedTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(false);
        newUser.setTaskList(taskListService.createTaskList());
        userService.save(newUser);
        TaskCreationRequest request = new TaskCreationRequest();
        request.setTitle("Title");
        request.setDescription("Description");
        request.setDueDate(LocalDate.parse("2024-05-07"));
        request.setNotificationTime(LocalTime.parse("09:00"));
        request.setNotificationMessage("Message");
        Task task = taskService.createTask(request);
        newUser.getTaskList().getTasks().add(task);
        UserTaskDueDateUpdateRequest request1 = new UserTaskDueDateUpdateRequest();
        request1.setUserId(newUser.getId());
        request1.setTaskId(task.getId());
        request1.setDueDate(LocalDate.parse("2024-05-21"));
        userService.updateTaskDueDate(request1);
        assertThat(taskService.searchForTaskById(task.getId()).getDueDate(), is(LocalDate.parse("2024-05-21")));
    }

    @Test
    public void createUser_createTask_updateTaskNotificationTime_timeIsUpdatedTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(false);
        newUser.setTaskList(taskListService.createTaskList());
        userService.save(newUser);
        TaskCreationRequest request = new TaskCreationRequest();
        request.setTitle("Title");
        request.setDescription("Description");
        request.setDueDate(LocalDate.parse("2024-05-07"));
        request.setNotificationTime(LocalTime.parse("09:00"));
        request.setNotificationMessage("Message");
        Task task = taskService.createTask(request);
        newUser.getTaskList().getTasks().add(task);
        UserNotificationTimeUpdateRequest request1 = new UserNotificationTimeUpdateRequest();
        request1.setUserId(newUser.getId());
        request1.setTaskId(task.getId());
        request1.setNotificationTime(LocalTime.parse("13:00"));
        userService.updateTaskNotificationTime(request1);
        assertThat(taskService.searchForTaskById(task.getId()).getNotification().getTime().toLocalTime(), is(LocalTime.parse("13:00")));
    }

     @Test
    public void createUser_createTask_searchForTaskByTitle_returnsTaskTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(false);
        newUser.setTaskList(taskListService.createTaskList());
        userService.save(newUser);
        TaskCreationRequest request = new TaskCreationRequest();
        request.setTitle("Title");
        request.setDescription("Description");
        request.setDueDate(LocalDate.parse("2024-05-07"));
        request.setNotificationTime(LocalTime.parse("09:00"));
        request.setNotificationMessage("Message");
        Task task = taskService.createTask(request);
        newUser.getTaskList().getTasks().add(task);
        SearchByTitleRequest searchByTitleRequest = new SearchByTitleRequest();
        searchByTitleRequest.setTitle("title");
        searchByTitleRequest.setUserId(newUser.getId());
         List<Task> result = userService.searchTaskByTitle(searchByTitleRequest);
         assertNotNull(result);
         assertEquals(1, result.size());
         assertEquals("title", result.get(0).getTitle());
         assertEquals(Status.CREATED, result.get(0).getStatus());
    }

    @Test
    public void createUser_createTask_searchForTaskByStatus_returnsTaskTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(false);
        newUser.setTaskList(taskListService.createTaskList());
        userService.save(newUser);
        TaskCreationRequest request = new TaskCreationRequest();
        request.setTitle("Title");
        request.setDescription("Description");
        request.setDueDate(LocalDate.parse("2024-05-07"));
        request.setNotificationTime(LocalTime.parse("09:00"));
        request.setNotificationMessage("Message");
        Task task = taskService.createTask(request);
        newUser.getTaskList().getTasks().add(task);
        SearchByStatusRequest search = new SearchByStatusRequest();
        search.setStatusNumber(1);
        search.setUserId(newUser.getId());
        List<Task> result = userService.searchTaskByStatus(search);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("title", result.get(0).getTitle());
        assertEquals(Status.CREATED, result.get(0).getStatus());
    }

    @Test
    public void createUser_createTask_searchForTaskByPriority_returnsTaskTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(false);
        newUser.setTaskList(taskListService.createTaskList());
        userService.save(newUser);
        TaskCreationRequest request = new TaskCreationRequest();
        request.setTitle("Title");
        request.setDescription("Description");
        request.setDueDate(LocalDate.parse("2024-05-07"));
        request.setNotificationTime(LocalTime.parse("09:00"));
        request.setNotificationMessage("Message");
        Task task = taskService.createTask(request);
        newUser.getTaskList().getTasks().add(task);
        SearchByPriorityRequest search = new SearchByPriorityRequest();
        search.setPriorityNumber(1);
        search.setUserId(newUser.getId());
        List<Task> result = userService.searchTaskByPriority(search);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("title", result.get(0).getTitle());
        assertEquals(Status.CREATED, result.get(0).getStatus());
    }

    @Test
    public void createUser_createTask_searchForTaskByDueDate_returnsTaskTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(false);
        newUser.setTaskList(taskListService.createTaskList());
        userService.save(newUser);
        TaskCreationRequest request = new TaskCreationRequest();
        request.setTitle("Title");
        request.setDescription("Description");
        request.setDueDate(LocalDate.parse("2024-05-07"));
        request.setNotificationTime(LocalTime.parse("09:00"));
        request.setNotificationMessage("Message");
        Task task = taskService.createTask(request);
        newUser.getTaskList().getTasks().add(task);
        SearchByDueDateRequest search = new SearchByDueDateRequest();
        search.setDueDate(LocalDate.parse("2024-05-07"));
        search.setUserId(newUser.getId());
        List<Task> result = userService.searchTaskByDueDate(search);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("title", result.get(0).getTitle());
        assertEquals(Status.CREATED, result.get(0).getStatus());
    }

    @Test
    public void createUser_createTask_deleteTask_taskIsDeletedTest(){
        User newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("email");
        newUser.setLocked(false);
        newUser.setTaskList(taskListService.createTaskList());
        userService.save(newUser);
        TaskCreationRequest request = new TaskCreationRequest();
        request.setTitle("Title");
        request.setDescription("Description");
        request.setDueDate(LocalDate.parse("2024-05-07"));
        request.setNotificationTime(LocalTime.parse("09:00"));
        request.setNotificationMessage("Message");
        Task task = taskService.createTask(request);
        newUser.getTaskList().getTasks().add(task);
        TaskDeleteRequest taskDeleteRequest = new TaskDeleteRequest();
        taskDeleteRequest.setTaskId(task.getId());
        taskDeleteRequest.setUserId(newUser.getId());
        userService.deleteTask(taskDeleteRequest);
        assertThat(userService.searchUserById(newUser.getId()).getTaskList().getTasks().size(), is(0));
    }
}
