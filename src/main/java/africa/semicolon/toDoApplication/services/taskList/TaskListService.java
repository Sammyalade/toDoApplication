package africa.semicolon.toDoApplication.services.taskList;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.TaskList;
import africa.semicolon.toDoApplication.dtos.request.AddTaskToTaskListRequest;

import java.util.List;

public interface TaskListService {

    TaskList createTaskList();
    void addTaskToTaskList(AddTaskToTaskListRequest addTaskToTaskListRequest);
    void removeTaskFromList(AddTaskToTaskListRequest addTaskToTaskListRequest);

    TaskList searchForTaskList(long taskListId);

    void save(TaskList taskList);
    List<Task> findAllTask(int taskListId);
}
