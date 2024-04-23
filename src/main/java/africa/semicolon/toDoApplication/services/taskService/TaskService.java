package africa.semicolon.toDoApplication.services.taskService;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.dtos.request.*;

import java.util.List;

public interface TaskService {

    Task createTask(TaskCreationRequest taskCreationRequest);
    void updateTaskTitle(TaskUpdateTitleRequest taskUpdateTitleRequest);
    void updateTaskDescription(TaskUpdateDescriptionRequest taskUpdateDescriptionRequest);
    void updateTaskPriority(TaskUpdatePriorityRequest taskUpdatepriorityRequest);
    void updateTaskDueDate(TaskUpdateDueDateRequest taskUpdateDueDateRequest);
    void updateTaskStatus(TaskUpdateStatusRequest taskUpdateStatusRequest);
    void updateTaskNotificationTime(TaskNotificationUpdateRequest taskNotificationUpdateRequest);
    void deleteTask(int id);
    Task searchForTaskById(int id);
    void save(Task task);
    List<Task> getAllTasks();

}
