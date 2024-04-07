package africa.semicolon.toDoApplication.services.taskService;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.repositories.TaskRepository;
import africa.semicolon.toDoApplication.dtos.TaskCreationRequest;
import africa.semicolon.toDoApplication.dtos.TaskDeleteRequest;
import africa.semicolon.toDoApplication.dtos.TaskSearchRequest;
import africa.semicolon.toDoApplication.dtos.TaskUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static africa.semicolon.toDoApplication.utility.Mapper.map;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public void createTask(TaskCreationRequest taskCreationRequest) {
        Task task = map(taskCreationRequest);
        taskRepository.save(task);
    }

    @Override
    public void updateTask(TaskUpdateRequest taskUpdateRequest) {

    }

    @Override
    public void deleteTask(TaskDeleteRequest taskDeleteRequest) {

    }

    @Override
    public void markTaskAsCompleted(TaskUpdateRequest taskUpdateRequest) {

    }

    @Override
    public void searchTask(TaskSearchRequest taskSearchRequest) {

    }

    @Override
    public void setTaskDueDate(TaskUpdateRequest taskUpdateRequest) {

    }

    @Override
    public void setTaskStatus(TaskUpdateRequest taskUpdateRequest) {

    }

    @Override
    public void setTaskReminder(TaskUpdateRequest taskUpdateRequest) {

    }
}
