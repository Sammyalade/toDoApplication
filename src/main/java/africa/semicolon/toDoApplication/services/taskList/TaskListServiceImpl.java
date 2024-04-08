package africa.semicolon.toDoApplication.services.taskList;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.TaskList;
import africa.semicolon.toDoApplication.datas.repositories.TaskListRepository;
import africa.semicolon.toDoApplication.dtos.AddTaskToTaskListRequest;
import africa.semicolon.toDoApplication.dtos.TaskCreationInTaskListRequest;
import africa.semicolon.toDoApplication.dtos.TaskSearchInTaskListRequest;
import africa.semicolon.toDoApplication.dtos.TaskUpdateInTaskListRequest;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static africa.semicolon.toDoApplication.utility.Mapper.checkIfListIsNull;

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
        Optional<TaskList> optionalTaskList = taskListRepository.findById(addTaskToTaskListRequest.getTaskListId());
        if(optionalTaskList.isPresent()) {
            TaskList taskList = optionalTaskList.get();
            taskList.setTasks((List<Task>) checkIfListIsNull(taskList.getTasks()));
            Optional<Task> task = taskService.searchForTaskById(addTaskToTaskListRequest.getTaskId());
            if(task.isPresent()) {
                taskList.getTasks().add(task.get());
                taskListRepository.save(taskList);
            }
        }
    }

    @Override
    public void removeTaskFromList(AddTaskToTaskListRequest addTaskToTaskListRequest) {
        Optional<TaskList> optionalTaskList = taskListRepository.findById(addTaskToTaskListRequest.getTaskListId());
        if(optionalTaskList.isPresent()) {
            TaskList taskList = optionalTaskList.get();
            taskList.setTasks((List<Task>) checkIfListIsNull(taskList.getTasks()));
            Optional<Task> task = taskService.searchForTaskById(addTaskToTaskListRequest.getTaskId());
            if (task.isPresent()) {
                taskList.getTasks().remove(task.get());
                taskListRepository.save(taskList);
            }
        }
    }


}
