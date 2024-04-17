package africa.semicolon.toDoApplication.services.taskService;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.dtos.request.TaskCreationRequest;
import africa.semicolon.toDoApplication.dtos.request.TaskUpdateRequest;

import java.util.List;

public interface TaskService {

    Task createTask(TaskCreationRequest taskCreationRequest);
    void updateTask(TaskUpdateRequest taskUpdateRequest);
    void deleteTask(int id);
    Task searchForTaskById(int id);
    void save(Task task);
    List<Task> getAllTasks();
}
