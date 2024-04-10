package africa.semicolon.toDoApplication.utility;

import africa.semicolon.toDoApplication.datas.models.*;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.dtos.response.TaskCreationResponse;
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
        response.setUsername(user.getUsername().toLowerCase());
        response.setEmail(user.getEmail());
        response.setTaskListId(user.getTaskList().getId());
        return response;
    }

    public static TaskCreationRequest map(UserTaskCreationRequest userTaskCreationRequest) {
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setTitle(userTaskCreationRequest.getTitle());
        taskCreationRequest.setDescription(userTaskCreationRequest.getDescription());
        taskCreationRequest.setDueDate(userTaskCreationRequest.getDueDate());
        taskCreationRequest.setNotificationTime(userTaskCreationRequest.getNotificationTime());
        taskCreationRequest.setNotificationMessage(userTaskCreationRequest.getNotificationMessage());
        return taskCreationRequest;
    }

    public static AddTaskToTaskListRequest map(long taskListId, int taskId){
        AddTaskToTaskListRequest addTaskToTaskListRequest = new AddTaskToTaskListRequest();
        addTaskToTaskListRequest.setTaskId(taskId);
        addTaskToTaskListRequest.setTaskListId(taskListId);
        return addTaskToTaskListRequest;
    }

    public static TaskCreationResponse map(int id, String taskTitle, int notificationId){
        TaskCreationResponse taskCreationResponse = new TaskCreationResponse();
        taskCreationResponse.setTaskId(id);
        taskCreationResponse.setTaskTitle(taskTitle);
        taskCreationResponse.setNotificationId(notificationId);
        return taskCreationResponse;
    }

    public static TaskUpdateRequest map(UserTaskUpdateRequest userTaskUpdateRequest) {
        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        taskUpdateRequest.setId(userTaskUpdateRequest.getTaskId());
        taskUpdateRequest.setTitle(userTaskUpdateRequest.getTitle());
        taskUpdateRequest.setDescription(userTaskUpdateRequest.getDescription());
        taskUpdateRequest.setStatus(userTaskUpdateRequest.getStatus());
        taskUpdateRequest.setMessage(userTaskUpdateRequest.getMessage());
        taskUpdateRequest.setRead(userTaskUpdateRequest.isRead());
        taskUpdateRequest.setPriority(userTaskUpdateRequest.getPriority());
        taskUpdateRequest.setDueDate(userTaskUpdateRequest.getDueDate());
        taskUpdateRequest.setTime(userTaskUpdateRequest.getTime());
        taskUpdateRequest.setNotificationId(userTaskUpdateRequest.getNotificationId());
        return taskUpdateRequest;
    }

}
