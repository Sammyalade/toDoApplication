package africa.semicolon.toDoApplication.services.taskService;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.dtos.request.TaskCreationRequest;
import africa.semicolon.toDoApplication.dtos.request.TaskDeleteRequest;
import africa.semicolon.toDoApplication.dtos.request.TaskUpdateRequest;

public interface TaskService {

    Task createTask(TaskCreationRequest taskCreationRequest);
    void updateTask(TaskUpdateRequest taskUpdateRequest);
    void deleteTask(TaskDeleteRequest taskDeleteRequest);
    Task searchForTaskById(int id);
}
