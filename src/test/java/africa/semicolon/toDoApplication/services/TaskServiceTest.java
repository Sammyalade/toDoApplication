package africa.semicolon.toDoApplication.services;

import africa.semicolon.toDoApplication.datas.models.Status;
import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.TaskList;
import africa.semicolon.toDoApplication.datas.repositories.NotificationRepository;
import africa.semicolon.toDoApplication.datas.repositories.TaskRepository;
import africa.semicolon.toDoApplication.dtos.TaskCreationRequest;
import africa.semicolon.toDoApplication.dtos.TaskDeleteRequest;
import africa.semicolon.toDoApplication.dtos.TaskUpdateRequest;
import africa.semicolon.toDoApplication.exception.EmptyStringException;
import africa.semicolon.toDoApplication.services.notificationService.NotificationService;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;

    @BeforeEach
    public void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    public void createTask_taskIsCreatedTest() {
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setStatus(Status.IN_PROGRESS);
        taskCreationRequest.setDueDate(LocalDate.parse("2021-12-31"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("09:00"));
        taskCreationRequest.setNotification(notificationService.createNotification("Message"));
        taskService.createTask(taskCreationRequest);
        assertThat(taskRepository.count(), is(1L));
    }

    @Test
    public void createTaskWithEmptyTitle_throwsExceptionTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setStatus(Status.IN_PROGRESS);
        taskCreationRequest.setDueDate(LocalDate.parse("2021-12-31"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("09:00"));
        taskCreationRequest.setNotification(notificationService.createNotification("Message"));
        assertThrows(EmptyStringException.class, () -> taskService.createTask(taskCreationRequest));
    }

    @Test
    public void createTask_updateTaskStatus_taskStatusIsUpdatedTest() {
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setStatus(Status.IN_PROGRESS);
        taskCreationRequest.setDueDate(LocalDate.parse("2021-12-31"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("09:00"));
        taskCreationRequest.setNotification(notificationService.createNotification("Message"));
        Task task = taskService.createTask(taskCreationRequest);
        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        taskUpdateRequest.setId(task.getId());
        taskUpdateRequest.setStatus(Status.COMPLETED);
        taskService.updateTaskStatus(taskUpdateRequest);
        Optional<Task> updatedTask = taskService.searchForTaskById(task.getId());
        Task task1 = updatedTask.get();
        assertThat(task1.getStatus(), is(Status.COMPLETED));
    }

    @Test
    public void createTask_updateTaskDueDate_taskDueDateIsUpdatedTest() {
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setStatus(Status.IN_PROGRESS);
        taskCreationRequest.setDueDate(LocalDate.parse("2021-12-31"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("09:00"));
        taskCreationRequest.setNotification(notificationService.createNotification("Message"));
        Task task = taskService.createTask(taskCreationRequest);
        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        taskUpdateRequest.setId(task.getId());
        taskUpdateRequest.setDueDate(LocalDate.parse("2024-12-29"));
        taskService.updateTaskDueDate(taskUpdateRequest);
        Optional<Task> updatedTask = taskService.searchForTaskById(task.getId());
        Task task1 = updatedTask.get();
        assertThat(task1.getDueDate(), is(LocalDate.parse("2024-12-29")));
    }

    @Test
    public void createTask_updateTaskDate_taskNotificationIsUpdatedTest() {
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setStatus(Status.IN_PROGRESS);
        taskCreationRequest.setDueDate(LocalDate.parse("2021-12-31"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("09:00"));
        taskCreationRequest.setNotification(notificationService.createNotification("Message"));
        Task task = taskService.createTask(taskCreationRequest);
        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        taskUpdateRequest.setId(task.getId());
        taskUpdateRequest.setDueDate(LocalDate.parse("2024-12-29"));
        taskService.updateTaskDueDate(taskUpdateRequest);
        Optional<Task> updatedTask = taskService.searchForTaskById(task.getId());
        Task task1 = updatedTask.get();
        assertThat(task1.getNotification().getTime(), is(LocalDateTime.of(LocalDate.parse("2024-12-29"), LocalTime.parse("09:00"))));
    }

    @Test
    public void createTaskDeleteTask_taskIsDeletedTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setStatus(Status.IN_PROGRESS);
        taskCreationRequest.setDueDate(LocalDate.parse("2021-12-31"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("09:00"));
        taskCreationRequest.setNotification(notificationService.createNotification("Message"));
        Task task = taskService.createTask(taskCreationRequest);
        TaskDeleteRequest taskDeleteRequest = new TaskDeleteRequest();
        taskDeleteRequest.setId(task.getId());
        taskService.deleteTask(taskDeleteRequest);
        assertThat(taskRepository.count(), is(0L));
        assertThat(notificationRepository.count(), is(0L));
    }
}
