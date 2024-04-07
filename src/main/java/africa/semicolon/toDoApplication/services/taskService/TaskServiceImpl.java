package africa.semicolon.toDoApplication.services.taskService;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.repositories.TaskRepository;
import africa.semicolon.toDoApplication.dtos.TaskCreationRequest;
import africa.semicolon.toDoApplication.dtos.TaskDeleteRequest;
import africa.semicolon.toDoApplication.dtos.TaskUpdateRequest;
import africa.semicolon.toDoApplication.exception.EmptyStringException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static africa.semicolon.toDoApplication.utility.Mapper.IsEmptyString;
import static africa.semicolon.toDoApplication.utility.Mapper.map;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public Task createTask(TaskCreationRequest taskCreationRequest) {
        if(IsEmptyString(taskCreationRequest.getTitle())) throw new EmptyStringException("Title cannot be empty");
        Task task = map(taskCreationRequest);
        taskRepository.save(task);
        return task;
    }



    @Override
    public void updateTaskStatus(TaskUpdateRequest taskUpdateRequest) {
        Optional<Task> task = searchForTaskById(taskUpdateRequest.getId());
        if(task.isPresent()) {
            task.get().setStatus(taskUpdateRequest.getStatus());
            taskRepository.save(task.get());
        }
    }

    @Override
    public void deleteTask(TaskDeleteRequest taskDeleteRequest) {

    }

    @Override
    public void updateTaskDueDate(TaskUpdateRequest taskUpdateRequest) {
        Optional<Task> task = searchForTaskById(taskUpdateRequest.getId());
        if(task.isPresent()) {
            task.get().setDueDate(taskUpdateRequest.getDueDate());
            taskRepository.save(task.get());
        }
    }

    @Override
    public void setTaskReminder(TaskUpdateRequest taskUpdateRequest) {

    }

    @Override
    public Optional<Task> searchForTaskById(int id) {
        return taskRepository.findById(id);
    }
}
