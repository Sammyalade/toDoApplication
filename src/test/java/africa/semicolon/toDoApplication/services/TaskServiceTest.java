package africa.semicolon.toDoApplication.services;

import africa.semicolon.toDoApplication.datas.models.Status;
import africa.semicolon.toDoApplication.datas.repositories.TaskRepository;
import africa.semicolon.toDoApplication.dtos.TaskCreationRequest;
import africa.semicolon.toDoApplication.services.notificationService.NotificationService;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    public void createTask_taskIsCreatedTest() {
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle("Title");
        taskCreationRequest.setDescription("Description");
        taskCreationRequest.setStatus(Status.IN_PROGRESS);
        taskCreationRequest.setNotification(notificationService.createNotification("Message"));
        taskService.createTask(taskCreationRequest);
        assertThat(taskRepository.count(), is(1L));
    }
}
