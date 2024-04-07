package africa.semicolon.toDoApplication.services.taskList;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.TaskList;
import africa.semicolon.toDoApplication.datas.repositories.TaskListRepository;
import africa.semicolon.toDoApplication.dtos.TaskCreationInTaskListRequest;
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
    public List<Task> getTaskListForUser(String username) {
        TaskList taskList = (TaskList) taskListRepository.findAll();
        return taskList.getTasks();
    }

    @Override
    public void createTaskInATaskList(TaskCreationInTaskListRequest taskCreationInTaskListRequest){
        Task task = taskService.createTask(taskCreationInTaskListRequest);
        Optional<TaskList> taskList = searchForTaskListById(taskCreationInTaskListRequest.getId());
        if(taskList.isPresent()){
            TaskList taskList1 = taskList.get();
            taskList1.setTasks((List<Task>) checkIfListIsNull(taskList1.getTasks()));
            taskList1.getTasks().add(task);
            taskListRepository.save(taskList1);
        }
    }

    @Override
    public Optional<TaskList> searchForTaskListById(long id) {
        return taskListRepository.findById(id);
    }

    @Override
    public void editTaskInTaskList(TaskUpdateInTaskListRequest taskUpdateInTaskListRequest) {
        Optional<TaskList> taskList = taskListRepository.findById(taskUpdateInTaskListRequest.getTaskListId());
        if(taskList.isPresent()){
            taskService.updateTaskDueDate(taskUpdateInTaskListRequest);
        }
    }
}
