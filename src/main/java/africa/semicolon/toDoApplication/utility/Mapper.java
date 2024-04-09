package africa.semicolon.toDoApplication.utility;

import africa.semicolon.toDoApplication.datas.models.*;
import africa.semicolon.toDoApplication.dtos.TaskCreationRequest;
import africa.semicolon.toDoApplication.dtos.TaskNotificationTimeChangeRequest;
import africa.semicolon.toDoApplication.dtos.UserRegistrationRequest;
import africa.semicolon.toDoApplication.dtos.response.UserRegistrationResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static africa.semicolon.toDoApplication.utility.Utility.mergeDateAndTime;

public class Mapper {

    public static Task map(TaskCreationRequest taskCreationRequest) {
        Task task = new Task();
        task.setTitle(taskCreationRequest.getTitle());
        task.setDescription(taskCreationRequest.getDescription());
        task.setStatus(Status.CREATED);
        Notification notification = taskCreationRequest.getNotification();
        notification.setTime(mergeDateAndTime(taskCreationRequest.getDueDate(),
                taskCreationRequest.getNotificationTime()));
        task.setNotification(notification);
        task.setPriority(Priority.NO_PRIORITY);
        task.setDueDate(taskCreationRequest.getDueDate());

        return task;
    }



    public static User map(UserRegistrationRequest userRegistrationRequest){
        User user = new User();
        user.setUsername(userRegistrationRequest.getUsername());
        user.setEmail(userRegistrationRequest.getEmail());
        TaskList taskList = new TaskList();
        user.setTaskList(taskList);
        return user;
    }

    public static UserRegistrationResponse map(User user){
        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        return response;
    }
}
