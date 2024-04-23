package africa.semicolon.toDoApplication.services;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.TaskList;
import africa.semicolon.toDoApplication.datas.repositories.TaskListRepository;
import africa.semicolon.toDoApplication.datas.repositories.TaskRepository;
import africa.semicolon.toDoApplication.dtos.request.AddTaskToTaskListRequest;
import africa.semicolon.toDoApplication.dtos.request.TaskCreationRequest;
import africa.semicolon.toDoApplication.exceptions.TaskListNotFoundException;
import africa.semicolon.toDoApplication.exceptions.TaskNotFoundException;
import africa.semicolon.toDoApplication.services.taskList.TaskListService;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@Transactional
public class TaskListServiceTest {

    @Autowired
    private TaskListService taskListService;
    @Autowired
    private TaskListRepository taskListRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        taskListRepository.deleteAll();
    }

    @Test
    public void createTaskListTest() {
        taskListService.createTaskList();
        assertThat(taskListRepository.count(), is(1L));
    }


    @Test
    public void createTaskAndAddToTaskListTest() {
        TaskList taskList = taskListService.createTaskList();
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("description");
        taskCreationRequest.setDueDate(LocalDate.parse("2021-12-31"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("09:00"));
        taskCreationRequest.setNotificationMessage("Message");
        Task task = taskService.createTask(taskCreationRequest);
        AddTaskToTaskListRequest addTaskToTaskListRequest = new AddTaskToTaskListRequest();
        addTaskToTaskListRequest.setTaskListId(taskList.getId());
        addTaskToTaskListRequest.setTaskId(task.getId());
        taskListService.addTaskToTaskList(addTaskToTaskListRequest);
        assertThat(taskList.getTasks().size(), is(1));
    }


    @Test
    public void createTaskAndRemoveFromTaskListTest() {
        TaskList taskList = taskListService.createTaskList();
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("description");
        taskCreationRequest.setDueDate(LocalDate.parse("2021-12-31"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("09:00"));
        taskCreationRequest.setNotificationMessage("Message");
        Task task = taskService.createTask(taskCreationRequest);
        AddTaskToTaskListRequest addTaskToTaskListRequest = new AddTaskToTaskListRequest();
        addTaskToTaskListRequest.setTaskListId(taskList.getId());
        addTaskToTaskListRequest.setTaskId(task.getId());
        taskListService.addTaskToTaskList(addTaskToTaskListRequest);
        taskListService.removeTaskFromList(addTaskToTaskListRequest);
        assertThat(taskList.getTasks().size(), is(0));
    }

    @Test
    public void createTask_addToTaskListThatDoesNotExist_throwsExceptionTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("description");
        taskCreationRequest.setDueDate(LocalDate.parse("2021-12-31"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("09:00"));
        taskCreationRequest.setNotificationMessage("Message");
        Task task = taskService.createTask(taskCreationRequest);
        AddTaskToTaskListRequest addTaskToTaskListRequest = new AddTaskToTaskListRequest();
        addTaskToTaskListRequest.setTaskListId(11);
        addTaskToTaskListRequest.setTaskId(task.getId());
        assertThatThrownBy(() -> {
            taskListService.addTaskToTaskList(addTaskToTaskListRequest);
        })
                .isInstanceOf(TaskListNotFoundException.class)
                .hasMessageContaining("TaskList not found");
    }

    @Test
    public void RemoveTaskFromTaskListThatDoesNotExist_throwsExceptionTest(){
        AddTaskToTaskListRequest addTaskToTaskListRequest = new AddTaskToTaskListRequest();
        addTaskToTaskListRequest.setTaskListId(11);
        addTaskToTaskListRequest.setTaskId(1);
        assertThatThrownBy(() -> {
            taskListService.addTaskToTaskList(addTaskToTaskListRequest);
        })
                .isInstanceOf(TaskListNotFoundException.class)
                .hasMessageContaining("TaskList not found");
    }

    @Test
    public void addTaskToTaskListThatDoesNotExist(){
        AddTaskToTaskListRequest addTaskToTaskListRequest = new AddTaskToTaskListRequest();
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("description");
        taskCreationRequest.setDueDate(LocalDate.parse("2021-12-31"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("09:00"));
        taskCreationRequest.setNotificationMessage("Message");
        Task task = taskService.createTask(taskCreationRequest);
        addTaskToTaskListRequest.setTaskListId(1);
        addTaskToTaskListRequest.setTaskId(task.getId());
        assertThatThrownBy(() -> {
            taskListService.addTaskToTaskList(addTaskToTaskListRequest);
        })
                .isInstanceOf(TaskListNotFoundException.class)
                .hasMessageContaining("TaskList not found");
    }

    @Test
    public void addNonExistingTaskToTaskList_throwsExceptionTest(){
        TaskList taskList = taskListService.createTaskList();
        AddTaskToTaskListRequest addTaskToTaskListRequest = new AddTaskToTaskListRequest();
        addTaskToTaskListRequest.setTaskListId(taskList.getId());
        addTaskToTaskListRequest.setTaskId(1);
        assertThatThrownBy(() -> {
            taskListService.addTaskToTaskList(addTaskToTaskListRequest);
        })
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task not found");
    }

    @Test
    public void findAllTaskTest(){
        TaskList taskList = taskListService.createTaskList();
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("description");
        taskCreationRequest.setDueDate(LocalDate.parse("2021-12-31"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("09:00"));
        taskCreationRequest.setNotificationMessage("Message");
        Task task = taskService.createTask(taskCreationRequest);
        AddTaskToTaskListRequest addTaskToTaskListRequest = new AddTaskToTaskListRequest();
        addTaskToTaskListRequest.setTaskListId(taskList.getId());
        addTaskToTaskListRequest.setTaskId(task.getId());
        taskListService.addTaskToTaskList(addTaskToTaskListRequest);
        taskListService.removeTaskFromList(addTaskToTaskListRequest);
        List<Task> tasks = taskListService.findAllTask(taskList.getId());
        assertThat(taskListService.findAllTask(taskList.getId()), is(tasks));
    }


    @Test
    public void createTaskAndAddToTaskList_deleteTaskFromTaskList_taskRepositoryIsEmptyTest(){
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("description");
        taskCreationRequest.setDueDate(LocalDate.parse("2021-12-31"));
        taskCreationRequest.setNotificationTime(LocalTime.parse("09:00"));
        taskCreationRequest.setNotificationMessage("Message");
        TaskList taskList = taskListService.createTaskList();
        Task task = taskService.createTask(taskCreationRequest);
        AddTaskToTaskListRequest addTaskToTaskListRequest = new AddTaskToTaskListRequest();
        addTaskToTaskListRequest.setTaskListId(taskList.getId());
        addTaskToTaskListRequest.setTaskId(task.getId());
        taskListService.addTaskToTaskList(addTaskToTaskListRequest);
        taskListService.removeTaskFromList(addTaskToTaskListRequest);
        assertThat(taskRepository.count(), is(0L));
    }

}
