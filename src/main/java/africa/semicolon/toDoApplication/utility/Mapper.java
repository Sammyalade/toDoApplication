package africa.semicolon.toDoApplication.utility;

import africa.semicolon.toDoApplication.datas.models.*;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.dtos.response.UserRegistrationResponse;

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

    public static NotificationUpdateRequest map(TaskUpdateRequest taskUpdateRequest){
        NotificationUpdateRequest notificationUpdateRequest = new NotificationUpdateRequest();
        notificationUpdateRequest.setDate(taskUpdateRequest.getDueDate());
        notificationUpdateRequest.setTime(taskUpdateRequest.getTime());
        notificationUpdateRequest.setMessage(taskUpdateRequest.getMessage());
        notificationUpdateRequest.setId(taskUpdateRequest.getNotificationId());
        notificationUpdateRequest.setRead(taskUpdateRequest.isRead());
        return notificationUpdateRequest;
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
