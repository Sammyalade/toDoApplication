package africa.semicolon.toDoApplication.services;

import africa.semicolon.toDoApplication.datas.models.Priority;
import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.Status;
import africa.semicolon.toDoApplication.datas.repositories.NotificationRepository;
import africa.semicolon.toDoApplication.datas.repositories.TaskRepository;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.exceptions.EmptyStringException;
import africa.semicolon.toDoApplication.exceptions.NumberNotFoundException;
import africa.semicolon.toDoApplication.services.notificationService.NotificationService;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


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
        notificationRepository.deleteAll();
    }

    @Test
    public void createTask_taskIsCreatedTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        taskService.createTask(taskCreationRequest);
        assertThat(taskService.getAllTasks().size(), is(1));
    }

    @Test
    public void createTask_updateTaskDescription_itIsUpdatedTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        Task task = taskService.createTask(taskCreationRequest);
        TaskUpdateDescriptionRequest taskUpdateDescriptionRequest = new TaskUpdateDescriptionRequest();
        taskUpdateDescriptionRequest.setTaskId(task.getId());
        taskUpdateDescriptionRequest.setDescription("NewDescription");
        taskService.updateTaskDescription(taskUpdateDescriptionRequest);
        assertThat(taskService.searchForTaskById(task.getId()).getDescription(), is("NewDescription"));
    }

    @Test
    public void createTaskWithBlankTitle_throwsExceptionTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("   ");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        assertThatThrownBy(()->{
           taskService.createTask(taskCreationRequest);
        })
                .isInstanceOf(EmptyStringException.class)
                .hasMessageContaining("Task Title cannot be blank, or empty or null");
    }

    @Test
    public void createTaskWithNullTitle_throwsExceptionTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle(null);
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        assertThatThrownBy(()->{
            taskService.createTask(taskCreationRequest);
        })
                .isInstanceOf(EmptyStringException.class)
                .hasMessageContaining("Task Title cannot be blank, or empty or null");
    }

    @Test
    public void createTaskWithEmptyStringTitle_throwsExceptionTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        assertThatThrownBy(()->{
            taskService.createTask(taskCreationRequest);
        })
                .isInstanceOf(EmptyStringException.class)
                .hasMessageContaining("Task Title cannot be blank, or empty or null");
    }


    @Test
    public void createTask_updateTaskTitle_titleIsUpdatedTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        Task task = taskService.createTask(taskCreationRequest);
        TaskUpdateTitleRequest taskUpdateTitleRequest = new TaskUpdateTitleRequest();
        taskUpdateTitleRequest.setTaskId(task.getId());
        taskUpdateTitleRequest.setTitle("New title");
        taskService.updateTaskTitle(taskUpdateTitleRequest);
        assertThat(taskService.searchForTaskById(task.getId()).getTitle(), is("New title"));
    }

    @Test
    public void createTask_updateTaskTitleWithBlankSpaces_throwsExceptionTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        Task task = taskService.createTask(taskCreationRequest);
        TaskUpdateTitleRequest taskUpdateTitleRequest = new TaskUpdateTitleRequest();
        taskUpdateTitleRequest.setTaskId(task.getId());
        taskUpdateTitleRequest.setTitle("   ");

        assertThatThrownBy(()->{
            taskService.updateTaskTitle(taskUpdateTitleRequest);
        })
                .isInstanceOf(EmptyStringException.class)
                .hasMessageContaining("Task Title cannot be blank, or empty or null");
    }

    @Test
    public void createTask_updateTaskTitleWithEmptyString_throwsExceptionTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        Task task = taskService.createTask(taskCreationRequest);
        TaskUpdateTitleRequest taskUpdateTitleRequest = new TaskUpdateTitleRequest();
        taskUpdateTitleRequest.setTaskId(task.getId());
        taskUpdateTitleRequest.setTitle("");

        assertThatThrownBy(()->{
            taskService.updateTaskTitle(taskUpdateTitleRequest);
        })
                .isInstanceOf(EmptyStringException.class)
                .hasMessageContaining("Task Title cannot be blank, or empty or null");
    }

    @Test
    public void createTask_updateTaskTitleWithNull_throwsExceptionTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        Task task = taskService.createTask(taskCreationRequest);
        TaskUpdateTitleRequest taskUpdateTitleRequest = new TaskUpdateTitleRequest();
        taskUpdateTitleRequest.setTaskId(task.getId());
        taskUpdateTitleRequest.setTitle(null);

        assertThatThrownBy(()->{
            taskService.updateTaskTitle(taskUpdateTitleRequest);
        })
                .isInstanceOf(EmptyStringException.class)
                .hasMessageContaining("Task Title cannot be blank, or empty or null");
    }

    @Test
    public void createTask_updateTaskPriority_priorityIsUpdatedTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        Task task = taskService.createTask(taskCreationRequest);
        TaskUpdatePriorityRequest taskUpdatePriorityRequest = new TaskUpdatePriorityRequest();
        taskUpdatePriorityRequest.setTaskId(task.getId());
        taskUpdatePriorityRequest.setPriorityNumber(2);
        taskService.updateTaskPriority(taskUpdatePriorityRequest);
        assertThat(taskService.searchForTaskById(task.getId()).getPriority(), is(Priority.LOW_PRIORITY));
    }

    @Test
    public void createTask_updateTaskPriorityWithWrongNumber_ExceptionIsThrownTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        Task task = taskService.createTask(taskCreationRequest);
        TaskUpdatePriorityRequest taskUpdatePriorityRequest = new TaskUpdatePriorityRequest();
        taskUpdatePriorityRequest.setTaskId(task.getId());
        taskUpdatePriorityRequest.setPriorityNumber(-55);
        assertThatThrownBy(()->{
            taskService.updateTaskPriority(taskUpdatePriorityRequest);
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
    public void createTask_changeTaskStatus_statusIsUpdatedTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        Task task = taskService.createTask(taskCreationRequest);
        TaskUpdateStatusRequest taskUpdateStatusRequest = new TaskUpdateStatusRequest();
        taskUpdateStatusRequest.setTaskId(task.getId());
        taskUpdateStatusRequest.setStatusNumber(2);
        taskService.updateTaskStatus(taskUpdateStatusRequest);
        assertThat(taskService.searchForTaskById(task.getId()).getStatus(), is(Status.IN_PROGRESS));
    }

    @Test
    public void createTask_changeTaskStatusWithWrongNumber_exceptionIsThrownTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        Task task = taskService.createTask(taskCreationRequest);
        TaskUpdateStatusRequest taskUpdateStatusRequest = new TaskUpdateStatusRequest();
        taskUpdateStatusRequest.setTaskId(task.getId());
        taskUpdateStatusRequest.setStatusNumber(-55);
        assertThatThrownBy(()->{
            taskService.updateTaskStatus(taskUpdateStatusRequest);
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
    public void createTask_changeNotificationTime_notificationTimeIsUpdatedTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        Task task = taskService.createTask(taskCreationRequest);
        TaskNotificationUpdateRequest taskNotificationUpdateRequest = new TaskNotificationUpdateRequest();
        taskNotificationUpdateRequest.setTaskId(task.getId());
        taskNotificationUpdateRequest.setNotificationTime(LocalTime.parse("02:00"));
        taskService.updateTaskNotificationTime(taskNotificationUpdateRequest);
        assertThat(taskService.searchForTaskById(task.getId()).getNotification().getTime().toLocalTime(), is(LocalTime.parse("02:00")));
    }

    @Test
    public void createTask_updateTaskDueDate_taskDueDateIsUpdatedTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        Task task = taskService.createTask(taskCreationRequest);
        TaskUpdateDueDateRequest taskUpdateDueDateRequest = new TaskUpdateDueDateRequest();
        taskUpdateDueDateRequest.setTaskId(task.getId());
        taskUpdateDueDateRequest.setDueDate(LocalDate.parse("2024-04-23"));
        taskService.updateTaskDueDate(taskUpdateDueDateRequest);
        assertThat(taskService.searchForTaskById(task.getId()).getDueDate(), is(LocalDate.parse("2024-04-23")));
    }

    @Test
    public void createTask_updateTaskDueDate_notificationDateIsUpdatedTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        Task task = taskService.createTask(taskCreationRequest);
        TaskUpdateDueDateRequest taskUpdateDueDateRequest = new TaskUpdateDueDateRequest();
        taskUpdateDueDateRequest.setTaskId(task.getId());
        taskUpdateDueDateRequest.setDueDate(LocalDate.parse("2024-04-23"));
        taskService.updateTaskDueDate(taskUpdateDueDateRequest);
        assertThat(taskService.searchForTaskById(task.getId()).getNotification().getTime().toLocalDate(), is(LocalDate.parse("2024-04-23")));
    }

    @Test
    public void createTask_deleteTask_taskIsDeletedTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        taskCreationRequest.setDueDate(LocalDate.parse("2024-04-20"));
        taskCreationRequest.setNotificationMessage("Notification Message");
        taskCreationRequest.setNotificationTime(LocalTime.parse("00:00"));
        Task task = taskService.createTask(taskCreationRequest);
        taskService.deleteTask(task.getId());
        assertThat(taskService.getAllTasks().size(), is(0));
    }
}
