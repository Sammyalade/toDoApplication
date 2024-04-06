package africa.semicolon.toDoApplication.services.taskList;

import africa.semicolon.toDoApplication.dtos.TaskListCreationRequest;
import africa.semicolon.toDoApplication.dtos.TaskListUpdateRequest;

public interface TaskListService {

    void createTaskList(TaskListCreationRequest taskListCreationRequest);
    void updateTaskList(TaskListUpdateRequest taskListUpdateRequest);
    void getTaskListForUser(String username);
    void deleteTaskList(String username);

}
