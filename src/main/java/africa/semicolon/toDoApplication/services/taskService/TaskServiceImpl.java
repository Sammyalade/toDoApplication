package africa.semicolon.toDoApplication.services.taskService;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.datas.models.Priority;
import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.datas.models.Status;
import africa.semicolon.toDoApplication.datas.repositories.TaskRepository;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.exceptions.EmptyStringException;
import africa.semicolon.toDoApplication.exceptions.NumberNotFoundException;
import africa.semicolon.toDoApplication.exceptions.TaskNotFoundException;
import africa.semicolon.toDoApplication.services.EmailService;
import africa.semicolon.toDoApplication.services.notificationService.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static africa.semicolon.toDoApplication.utility.Mapper.map;
import static africa.semicolon.toDoApplication.utility.Utility.isEmptyOrNullString;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private EmailService emailService;

    @Override
    public Task createTask(TaskCreationRequest taskCreationRequest) {
        if(isEmptyOrNullString(taskCreationRequest.getTitle())) throw new EmptyStringException("Task Title cannot be blank, or empty or null");
        Notification notification = notificationService.createNotification(taskCreationRequest.getNotificationMessage());
        taskCreationRequest.setNotification(notification);
        Task task = map(taskCreationRequest);
        notificationService.save(task.getNotification());
        taskRepository.save(task);
        return task;
    }

    @Override
    public void updateTaskTitle(TaskUpdateTitleRequest taskUpdateTitleRequest) {
        if(isEmptyOrNullString(taskUpdateTitleRequest.getTitle())) throw new EmptyStringException("Task Title cannot be blank, or empty or null");
        Task task = searchForTaskById(taskUpdateTitleRequest.getTaskId());
        task.setTitle(taskUpdateTitleRequest.getTitle());
        taskRepository.save(task);
    }

    @Override
    public void updateTaskDescription(TaskUpdateDescriptionRequest taskUpdateDescriptionRequest) {
        Task task = searchForTaskById(taskUpdateDescriptionRequest.getTaskId());
        task.setDescription(taskUpdateDescriptionRequest.getDescription());
        taskRepository.save(task);
    }

    @Override
    public void updateTaskPriority(TaskUpdatePriorityRequest taskUpdatepriorityRequest) {
        Task task = searchForTaskById(taskUpdatepriorityRequest.getTaskId());
        switch(taskUpdatepriorityRequest.getPriorityNumber()){
            case 1 -> task.setPriority(Priority.NO_PRIORITY);
            case 2 -> task.setPriority(Priority.LOW_PRIORITY);
            case 3 -> task.setPriority(Priority.MEDIUM_PRIORITY);
            case 4 -> task.setPriority(Priority.HIGH_PRIORITY);
            default -> throw new NumberNotFoundException("""
                    Task Priority number is between 1 to 4
                    1-> NO_PRIORITY
                    2-> LOW_PRIORITY
                    3-> MEDIUM_PRIORITY
                    4-> HIGH_PRIORITY
                    """);
        }
        taskRepository.save(task);
    }

    @Override
    public void updateTaskDueDate(TaskUpdateDueDateRequest taskUpdateDueDateRequest) {
        Task task = searchForTaskById(taskUpdateDueDateRequest.getTaskId());
        task.setDueDate(taskUpdateDueDateRequest.getDueDate());
        notificationService.updateNotification(map(taskUpdateDueDateRequest, task.getNotification().getId()));
        taskRepository.save(task);
    }



    @Override
    public void updateTaskStatus(TaskUpdateStatusRequest taskUpdateStatusRequest) {
        Task task = searchForTaskById(taskUpdateStatusRequest.getTaskId());
        switch(taskUpdateStatusRequest.getStatusNumber()){
            case 1 -> task.setStatus(Status.CREATED);
            case 2 -> task.setStatus(Status.IN_PROGRESS);
            case 3 -> task.setStatus(Status.COMPLETED);
            case 4 -> task.setStatus(Status.ON_HOLD);
            case 5 -> task.setStatus(Status.CANCELLED);
            default -> throw new NumberNotFoundException("""
                    Task Status number is between 1 to 5
                    1-> CREATED
                    2-> IN_PROGRESS
                    3-> COMPLETED
                    4-> ON_HOLD
                    5-> CANCELLED
                    """);
        }
        taskRepository.save(task);
    }

@Override
public void updateTaskNotificationTime(TaskNotificationUpdateRequest taskNotificationUpdateRequest) {
Task task = searchForTaskById(taskNotificationUpdateRequest.getTaskId());
notificationService.updateNotification(map(taskNotificationUpdateRequest, task));
}



@Override
public void deleteTask(int id) {
Task task = searchForTaskById(id);
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



@Override
public void save(Task task) {
taskRepository.save(task);
}

@Override
public List<Task> getAllTasks() {
return taskRepository.findAll();
}

}
