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
public class TaskListTest {

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
        TaskCreationInTaskListRequest taskCreationInTaskListRequest = new TaskCreationInTaskListRequest();
        taskCreationInTaskListRequest.setId(taskList.getId());
        taskCreationInTaskListRequest.setTitle("Title");
        taskCreationInTaskListRequest.setDescription("description");
        taskCreationInTaskListRequest.setStatus(Status.IN_PROGRESS);
        taskCreationInTaskListRequest.setDueDate(LocalDate.parse("2021-12-31"));
        taskCreationInTaskListRequest.setNotificationTime(LocalTime.parse("09:00"));
        taskCreationInTaskListRequest.setNotification(notificationService.createNotification("Message"));
        taskListService.createTaskInATaskList(taskCreationInTaskListRequest);
        Optional<TaskList> optionalTaskList = taskListService.searchForTaskListById(taskList.getId());
        TaskList updatedTaskList = optionalTaskList.get();
        assertThat(updatedTaskList.getTasks(), hasSize(1));
        assertThat(taskListRepository.count(), is(1L));
        assertThat(taskRepository.count(), is(1L));
    }

    @Transactional
    @Test
    public void createTask_updateTaskDueDate_taskDueDateIsUpdatedTest() {
        TaskList taskList = taskListService.createTaskList();
        TaskCreationInTaskListRequest taskCreationInTaskListRequest = new TaskCreationInTaskListRequest();
        taskCreationInTaskListRequest.setTitle("Title");
        taskCreationInTaskListRequest.setDescription("Description");
        taskCreationInTaskListRequest.setStatus(Status.IN_PROGRESS);
        taskCreationInTaskListRequest.setDueDate(LocalDate.parse("2021-12-31"));
        taskCreationInTaskListRequest.setNotificationTime(LocalTime.parse("09:00"));
        taskCreationInTaskListRequest.setNotification(notificationService.createNotification("Message"));
        taskCreationInTaskListRequest.setId(taskList.getId());
        Task task = taskListService.createTaskInATaskList(taskCreationInTaskListRequest);
        TaskUpdateInTaskListRequest taskUpdateInTaskListRequest = new TaskUpdateInTaskListRequest();
        taskUpdateInTaskListRequest.setTaskListId(taskList.getId());
        taskCreationInTaskListRequest.setId(task.getId());
        taskUpdateInTaskListRequest.setDueDate(LocalDate.parse("2024-12-29"));
        taskListService.editTaskInTaskList(taskUpdateInTaskListRequest);
        TaskSearchInTaskListRequest taskSearchInTaskListRequest = new TaskSearchInTaskListRequest();
        taskSearchInTaskListRequest.setTaskId(task.getId());
        taskSearchInTaskListRequest.setTaskListId(taskList.getId());
        Task updatedTask = taskListService.searchForTaskById(taskSearchInTaskListRequest);
        assertThat(updatedTask.getDueDate(), is(LocalDate.parse("2024-12-29")));
    }
}
