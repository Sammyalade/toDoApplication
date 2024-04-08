package africa.semicolon.toDoApplication.services;

import africa.semicolon.toDoApplication.datas.models.Status;
import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.TaskList;
import africa.semicolon.toDoApplication.datas.repositories.TaskListRepository;
import africa.semicolon.toDoApplication.datas.repositories.TaskRepository;
import africa.semicolon.toDoApplication.dtos.*;
import africa.semicolon.toDoApplication.services.notificationService.NotificationService;
import africa.semicolon.toDoApplication.services.taskList.TaskListService;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class TaskListServiceTest {

    @Autowired
    private TaskListService taskListService;
    @Autowired
    private TaskListRepository taskListRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskService taskService;

    @Test
    public void createTaskListTest() {
        taskListService.createTaskList();
        assertThat(taskListRepository.count(), is(1L));
    }

    @Transactional
    @Test
    public void createTaskAndAddToTaskListTest() {
        TaskList taskList = taskListService.createTaskList();
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("description");
        taskCreationRequest.setStatus(Status.IN_PROGRESS);
        taskCreationRequest.setDueDate(LocalDate.parse("2021-12-31"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("09:00"));
        taskCreationRequest.setNotification(notificationService.createNotification("Message"));
        Task task = taskService.createTask(taskCreationRequest);
        AddTaskToTaskListRequest addTaskToTaskListRequest = new AddTaskToTaskListRequest();
        addTaskToTaskListRequest.setTaskListId(taskList.getId());
        addTaskToTaskListRequest.setTaskId(task.getId());
        taskListService.addTaskToTaskList(addTaskToTaskListRequest);
        assertThat(taskList.getTasks().size(), is(1));
    }

    @Transactional
    @Test
    public void createTaskAndRemoveFromTaskListTest() {
        TaskList taskList = taskListService.createTaskList();
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("description");
        taskCreationRequest.setStatus(Status.IN_PROGRESS);
        taskCreationRequest.setDueDate(LocalDate.parse("2021-12-31"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("09:00"));
        taskCreationRequest.setNotification(notificationService.createNotification("Message"));
        Task task = taskService.createTask(taskCreationRequest);
        AddTaskToTaskListRequest addTaskToTaskListRequest = new AddTaskToTaskListRequest();
        addTaskToTaskListRequest.setTaskListId(taskList.getId());
        addTaskToTaskListRequest.setTaskId(task.getId());
        taskListService.addTaskToTaskList(addTaskToTaskListRequest);
        taskListService.removeTaskFromList(addTaskToTaskListRequest);
        assertThat(taskList.getTasks().size(), is(0));
    }

    @Test
    public void

}