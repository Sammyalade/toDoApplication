package africa.semicolon.toDoApplication.utility;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.dtos.TaskCreationRequest;
import africa.semicolon.toDoApplication.dtos.TaskNotificationTimeChangeRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static Task map(TaskCreationRequest taskCreationRequest) {
        Task task = new Task();
        task.setTitle(taskCreationRequest.getTitle());
        task.setDescription(taskCreationRequest.getDescription());
        task.setStatus(taskCreationRequest.getStatus());
        Notification notification = taskCreationRequest.getNotification();
        notification.setTime(mergeDateAndTime(taskCreationRequest.getDueDate(),
                taskCreationRequest.getNotificationTime()));
        task.setNotification(notification);
        task.setDueDate(taskCreationRequest.getDueDate());

        return task;
    }

    private static LocalDateTime mergeDateAndTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }

    public static boolean IsEmptyString(String title) {
        return title.isEmpty();
    }

    public static TaskNotificationTimeChangeRequest map(int id, LocalDate date){
        TaskNotificationTimeChangeRequest taskNotificationTimeChangeRequest = new TaskNotificationTimeChangeRequest();
        taskNotificationTimeChangeRequest.setId(id);
        taskNotificationTimeChangeRequest.setTime(date);
        return taskNotificationTimeChangeRequest;
    }

    public static List<?> checkIfListIsNull(List<?> lists) {
        if(lists == null) lists = new ArrayList<>();

        return lists;
    }
}
