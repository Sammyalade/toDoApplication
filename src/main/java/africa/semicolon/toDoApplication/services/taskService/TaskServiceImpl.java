package africa.semicolon.toDoApplication.services.taskService;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.repositories.NotificationRepository;
import africa.semicolon.toDoApplication.datas.repositories.TaskRepository;
import africa.semicolon.toDoApplication.dtos.TaskCreationRequest;
import africa.semicolon.toDoApplication.dtos.TaskDeleteRequest;
import africa.semicolon.toDoApplication.dtos.TaskNotificationTimeChangeRequest;
import africa.semicolon.toDoApplication.dtos.TaskUpdateRequest;
import africa.semicolon.toDoApplication.exception.EmptyStringException;
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
    private NotificationRepository notificationRepository;
    @Override
    public Task createTask(TaskCreationRequest taskCreationRequest) {
        if(IsEmptyString(taskCreationRequest.getTitle())) throw new EmptyStringException("Title cannot be empty");
        Task task = map(taskCreationRequest);
        Notification notification = task.getNotification();
        notificationRepository.save(notification);
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
        Optional<Task> task = searchForTaskById(taskDeleteRequest.getId());
        if(task.isPresent()) {
            Notification notification = task.get().getNotification();
            taskRepository.delete(task.get());
            notificationRepository.delete(notification);
        }
    }

    @Override
    public void updateTaskDueDate(TaskUpdateRequest taskUpdateRequest) {
        updateTaskNotificationTime(map(taskUpdateRequest.getId(), taskUpdateRequest.getDueDate()));
        Optional<Task> task = searchForTaskById(taskUpdateRequest.getId());
        if(task.isPresent()) {
            task.get().setDueDate(taskUpdateRequest.getDueDate());

            taskRepository.save(task.get());
        }
    }

    @Override
    public void updateTaskNotificationTime(TaskNotificationTimeChangeRequest taskNotificationTimeChangeRequest) {
        Optional<Task> task = searchForTaskById(taskNotificationTimeChangeRequest.getId());
        if(task.isPresent()) {
            LocalDateTime dateTime = task.get().getNotification().getTime();
            LocalTime time = dateTime.toLocalTime();
            dateTime = LocalDateTime.of(taskNotificationTimeChangeRequest.getTime(), time);
            task.get().getNotification().setTime(dateTime);
            notificationRepository.save(task.get().getNotification());
            taskRepository.save(task.get());
        }
    }

    @Override
    public Optional<Task> searchForTaskById(int id) {
        return taskRepository.findById(id);
    }
}
