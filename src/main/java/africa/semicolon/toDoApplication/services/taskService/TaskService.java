package africa.semicolon.toDoApplication.services.taskService;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.dtos.TaskCreationRequest;
import africa.semicolon.toDoApplication.dtos.TaskDeleteRequest;
import africa.semicolon.toDoApplication.dtos.TaskNotificationTimeChangeRequest;
import africa.semicolon.toDoApplication.dtos.TaskUpdateRequest;

import java.time.LocalTime;
import java.util.Optional;

public interface TaskService {

    Task createTask(TaskCreationRequest taskCreationRequest);
    void updateTaskStatus(TaskUpdateRequest taskUpdateRequest);
    void deleteTask(TaskDeleteRequest taskDeleteRequest);
    void updateTaskDueDate(TaskUpdateRequest taskUpdateRequest);
    void updateTaskNotificationTime(TaskNotificationTimeChangeRequest taskNotificationTimeChangeRequest);
    Optional<Task> searchForTaskById(int id);
}
