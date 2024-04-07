package africa.semicolon.toDoApplication.services.taskList;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.TaskList;
import africa.semicolon.toDoApplication.dtos.TaskCreationInTaskListRequest;
import africa.semicolon.toDoApplication.dtos.TaskUpdateInTaskListRequest;

import java.util.List;
import java.util.Optional;

public interface TaskListService {

    TaskList createTaskList();
    List<Task> getTaskListForUser(String username);

    void createTaskInATaskList(TaskCreationInTaskListRequest taskCreationInTaskListRequest);

    Optional<TaskList> searchForTaskListById(long id);

    void editTaskInTaskList(TaskUpdateInTaskListRequest taskUpdateInTaskListRequest);
}
