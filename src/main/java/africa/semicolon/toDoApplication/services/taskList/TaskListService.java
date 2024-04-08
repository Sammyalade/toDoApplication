package africa.semicolon.toDoApplication.services.taskList;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.TaskList;
import africa.semicolon.toDoApplication.dtos.AddTaskToTaskListRequest;
import africa.semicolon.toDoApplication.dtos.TaskCreationInTaskListRequest;
import africa.semicolon.toDoApplication.dtos.TaskSearchInTaskListRequest;
import africa.semicolon.toDoApplication.dtos.TaskUpdateInTaskListRequest;

import java.util.List;
import java.util.Optional;

public interface TaskListService {

    TaskList createTaskList();
    void addTaskToTaskList(AddTaskToTaskListRequest addTaskToTaskListRequest);

    void removeTaskFromList(AddTaskToTaskListRequest addTaskToTaskListRequest);
}
