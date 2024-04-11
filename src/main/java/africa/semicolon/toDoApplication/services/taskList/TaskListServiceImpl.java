package africa.semicolon.toDoApplication.services.taskList;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.TaskList;
import africa.semicolon.toDoApplication.datas.repositories.TaskListRepository;
import africa.semicolon.toDoApplication.dtos.request.AddTaskToTaskListRequest;
import africa.semicolon.toDoApplication.exception.TaskListNotFoundException;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static africa.semicolon.toDoApplication.utility.Utility.checkIfListIsNull;

@Service
public class TaskListServiceImpl implements TaskListService{

    @Autowired
    private TaskListRepository taskListRepository;
    @Autowired
    private TaskService taskService;

    @Override
    public TaskList createTaskList() {
        TaskList taskList = new TaskList();
        taskListRepository.save(taskList);
        return taskList;
    }

    @Override
    public void addTaskToTaskList(AddTaskToTaskListRequest addTaskToTaskListRequest) {
        TaskList taskList = searchForTaskList(addTaskToTaskListRequest.getTaskListId());
        taskList.setTasks((List<Task>) checkIfListIsNull(taskList.getTasks()));
        Task task = taskService.searchForTaskById(addTaskToTaskListRequest.getTaskId());
        taskList.getTasks().add(task);
        taskListRepository.save(taskList);


    }

    @Override
    public void removeTaskFromList(AddTaskToTaskListRequest addTaskToTaskListRequest) {
        TaskList taskList = searchForTaskList(addTaskToTaskListRequest.getTaskListId());
        Task task = taskService.searchForTaskById(addTaskToTaskListRequest.getTaskId());
        taskList.getTasks().remove(task);
        taskService.deleteTask(task.getId());
        taskListRepository.save(taskList);
    }

    @Override
    public TaskList searchForTaskList(long taskListId) {
        Optional<TaskList> optionalTaskList = taskListRepository.findById(taskListId);
        if(optionalTaskList.isPresent()) {
            return optionalTaskList.get();
        }
        throw new TaskListNotFoundException("TaskList not found");
    }

    @Override
    public void save(TaskList taskList) {
        taskListRepository.save(taskList);
    }

    @Override
    public List<Task> findAllTask(int taskListId) {
        return searchForTaskList(taskListId).getTasks();
    }


}
