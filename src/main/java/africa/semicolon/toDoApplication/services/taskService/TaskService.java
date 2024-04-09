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
    void updateTask(TaskUpdateRequest taskUpdateRequest);
    void deleteTask(TaskDeleteRequest taskDeleteRequest);
    Task searchForTaskById(int id);
}
