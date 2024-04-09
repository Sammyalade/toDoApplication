package africa.semicolon.toDoApplication.services.taskService;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.repositories.TaskRepository;
import africa.semicolon.toDoApplication.dtos.request.TaskCreationRequest;
import africa.semicolon.toDoApplication.dtos.request.TaskDeleteRequest;
import africa.semicolon.toDoApplication.dtos.request.TaskUpdateRequest;
import africa.semicolon.toDoApplication.exception.EmptyStringException;
import africa.semicolon.toDoApplication.exception.TaskNotFoundException;
import africa.semicolon.toDoApplication.services.notificationService.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static africa.semicolon.toDoApplication.utility.Mapper.map;
import static africa.semicolon.toDoApplication.utility.Utility.IsEmptyOrNullString;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private NotificationService notificationService;

    @Override
    public Task createTask(TaskCreationRequest taskCreationRequest) {
        if(IsEmptyOrNullString(taskCreationRequest.getTitle())) throw new EmptyStringException("Title cannot be empty");
        Task task = map(taskCreationRequest);
        Notification notification = task.getNotification();
        notificationService.save(notification);
        taskRepository.save(task);
        return task;
    }



    @Override
    public void updateTask(TaskUpdateRequest taskUpdateRequest) {
        Task task = searchForTaskById(taskUpdateRequest.getId());
        checkForUpdate(taskUpdateRequest, task);
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(TaskDeleteRequest taskDeleteRequest) {
        Task task = searchForTaskById(taskDeleteRequest.getId());
        Notification notification = task.getNotification();
        taskRepository.delete(task);
        notificationService.delete(notification);
    }

    @Override
    public Task searchForTaskById(int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()) {
            return optionalTask.get();
        }
        throw new TaskNotFoundException("Task not found");
    }






    private void checkForUpdate(TaskUpdateRequest taskUpdateRequest, Task task) {
        if(taskUpdateRequest.getTitle() != null) task.setTitle(taskUpdateRequest.getTitle());
        if(taskUpdateRequest.getDescription() != null) task.setDescription(taskUpdateRequest.getDescription());
        if(taskUpdateRequest.getDueDate() != null) task.setDueDate(taskUpdateRequest.getDueDate());
        if(taskUpdateRequest.getMessage() != null) task.getNotification().setMessage(taskUpdateRequest.getMessage());
        notificationService.updateNotification(map(taskUpdateRequest));
        task.setPriority(taskUpdateRequest.getPriority());
        task.setStatus(taskUpdateRequest.getStatus());
    }

}
