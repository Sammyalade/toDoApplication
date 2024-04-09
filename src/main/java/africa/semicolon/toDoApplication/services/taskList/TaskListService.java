package africa.semicolon.toDoApplication.services.taskList;

import africa.semicolon.toDoApplication.datas.models.TaskList;
import africa.semicolon.toDoApplication.dtos.request.AddTaskToTaskListRequest;

public interface TaskListService {

    TaskList createTaskList();
    void addTaskToTaskList(AddTaskToTaskListRequest addTaskToTaskListRequest);
    void removeTaskFromList(AddTaskToTaskListRequest addTaskToTaskListRequest);
}
