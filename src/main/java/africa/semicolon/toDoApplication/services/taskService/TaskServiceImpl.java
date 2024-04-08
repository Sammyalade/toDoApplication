package africa.semicolon.toDoApplication.services.taskService;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.repositories.TaskRepository;
import africa.semicolon.toDoApplication.dtos.TaskCreationRequest;
import africa.semicolon.toDoApplication.dtos.TaskDeleteRequest;
import africa.semicolon.toDoApplication.dtos.TaskNotificationTimeChangeRequest;
import africa.semicolon.toDoApplication.dtos.TaskUpdateRequest;
import africa.semicolon.toDoApplication.exception.EmptyStringException;
import africa.semicolon.toDoApplication.exception.TaskNotFoundException;
import africa.semicolon.toDoApplication.services.notificationService.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static africa.semicolon.toDoApplication.utility.Mapper.IsEmptyString;
import static africa.semicolon.toDoApplication.utility.Mapper.map;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private NotificationService notificationService;

    @Override
    public Task createTask(TaskCreationRequest taskCreationRequest) {
        if(IsEmptyString(taskCreationRequest.getTitle())) throw new EmptyStringException("Title cannot be empty");
        Task task = map(taskCreationRequest);
        Notification notification = task.getNotification();
        notificationService.save(notification);
        taskRepository.save(task);
        return task;
    }



    @Override
    public void updateTaskStatus(TaskUpdateRequest taskUpdateRequest) {
        Task task = searchForTaskById(taskUpdateRequest.getId());
        task.setStatus(taskUpdateRequest.getStatus());
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
    public void updateTaskDueDate(TaskUpdateRequest taskUpdateRequest) {
        updateTaskNotificationTime(map(taskUpdateRequest.getId(), taskUpdateRequest.getDueDate()));
        Task task = searchForTaskById(taskUpdateRequest.getId());
        task.setDueDate(taskUpdateRequest.getDueDate());
        taskRepository.save(task);
    }

    @Override
    public void updateTaskNotificationTime(TaskNotificationTimeChangeRequest taskNotificationTimeChangeRequest) {
        Task task = searchForTaskById(taskNotificationTimeChangeRequest.getId());
        LocalDateTime dateTime = task.getNotification().getTime();
        LocalTime time = dateTime.toLocalTime();
        dateTime = LocalDateTime.of(taskNotificationTimeChangeRequest.getTime(), time);
        task.getNotification().setTime(dateTime);
        notificationService.save(task.getNotification());
        taskRepository.save(task);

    }

    @Override
    public Task searchForTaskById(int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()) {
            return optionalTask.get();
        }
        throw new TaskNotFoundException("Task not found");
    }
}
