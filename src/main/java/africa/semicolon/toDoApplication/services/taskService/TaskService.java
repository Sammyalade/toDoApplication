package africa.semicolon.toDoApplication.services.taskService;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.dtos.TaskCreationRequest;
import africa.semicolon.toDoApplication.dtos.TaskDeleteRequest;
import africa.semicolon.toDoApplication.dtos.TaskSearchRequest;
import africa.semicolon.toDoApplication.dtos.TaskUpdateRequest;

import java.util.List;

public interface TaskService {

    void createTask(TaskCreationRequest taskCreationRequest);
    void updateTask(TaskUpdateRequest taskUpdateRequest);
    void deleteTask(TaskDeleteRequest taskDeleteRequest);
    void markTaskAsCompleted(TaskUpdateRequest taskUpdateRequest);
    void searchTask(TaskSearchRequest taskSearchRequest);
    void setTaskDueDate(TaskUpdateRequest taskUpdateRequest);
    void setTaskStatus(TaskUpdateRequest taskUpdateRequest);
    void setTaskReminder(TaskUpdateRequest taskUpdateRequest);

}
