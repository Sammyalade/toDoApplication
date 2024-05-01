package africa.semicolon.toDoApplication.utility;

import africa.semicolon.toDoApplication.datas.models.*;
import africa.semicolon.toDoApplication.dtos.request.*;
import africa.semicolon.toDoApplication.dtos.response.*;

import static africa.semicolon.toDoApplication.utility.Utility.mergeDateAndTime;

public class Mapper {

    public static Task map(TaskCreationRequest taskCreationRequest) {
        Task task = new Task();
        task.setTitle(taskCreationRequest.getTitle().toLowerCase());
        task.setDescription(taskCreationRequest.getDescription().toLowerCase());
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
        user.setUsername(userRegistrationRequest.getUsername().toLowerCase());
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


    public static UserUpdateResponse map(UserUpdateRequest userUpdateRequest) {
        UserUpdateResponse userUpdateResponse = new UserUpdateResponse();
        userUpdateResponse.setEmail(userUpdateRequest.getEmail());
        userUpdateResponse.setUserId(userUpdateRequest.getId());
        userUpdateResponse.setUsername(userUpdateRequest.getUsername());
        return userUpdateResponse;
    }

    public static TaskCreationRequest map(TaskAssignmentRequest taskAssignment) {
        TaskCreationRequest taskCreationRequest = new TaskCreationRequest();
        taskCreationRequest.setNotificationMessage(taskAssignment.getNotificationMessage());
        taskCreationRequest.setNotificationTime(taskAssignment.getNotificationTime());
        taskCreationRequest.setTitle(taskAssignment.getTitle());
        taskCreationRequest.setDueDate(taskAssignment.getDueDate());
        taskCreationRequest.setDescription(taskAssignment.getDescription());
        return taskCreationRequest;
    }

    public static TaskCreationRequest map(AssignTaskToOldUserRequest taskAssignment) {
        TaskCreationRequest taskCreation = new TaskCreationRequest();
        taskCreation.setNotificationMessage(taskAssignment.getNotificationMessage());
        taskCreation.setNotificationTime(taskAssignment.getNotificationTime());
        taskCreation.setTitle(taskAssignment.getTitle());
        taskCreation.setDueDate(taskAssignment.getDueDate());
        taskCreation.setDescription(taskAssignment.getDescription());
        return taskCreation;
    }

    public static AssignTaskToNewUserResponse map(String assignorUsername, String assigneeUsername, String title, int taskId, int userId) {
        AssignTaskToNewUserResponse response = new AssignTaskToNewUserResponse();
        response.setAssigneeUsername(assigneeUsername);
        response.setAssignorUsername(assignorUsername);
        response.setTaskName(title);
        response.setTaskId(taskId);
        response.setNewUserId(userId);
        return response;
    }

    public static NotificationUpdateRequest map(TaskUpdateDueDateRequest taskUpdateDueDateRequest, int notificationId) {
        NotificationUpdateRequest notificationUpdateRequest = new NotificationUpdateRequest();
        notificationUpdateRequest.setId(notificationId);
        notificationUpdateRequest.setDate(taskUpdateDueDateRequest.getDueDate());
        return notificationUpdateRequest;
    }

    public static NotificationUpdateRequest map(TaskNotificationUpdateRequest taskNotificationUpdateRequest, Task task) {
        NotificationUpdateRequest notificationUpdateRequest = new NotificationUpdateRequest();
        notificationUpdateRequest.setId(task.getNotification().getId());
        notificationUpdateRequest.setTime(taskNotificationUpdateRequest.getNotificationTime());
        return notificationUpdateRequest;
    }

    public static TaskUpdateTitleRequest map(UserTaskTitleUpdateRequest userTaskTitleUpdateRequest) {
        TaskUpdateTitleRequest taskUpdateTitleRequest = new TaskUpdateTitleRequest();
        taskUpdateTitleRequest.setTitle(userTaskTitleUpdateRequest.getTitle().toLowerCase());
        taskUpdateTitleRequest.setTaskId(userTaskTitleUpdateRequest.getTaskId());
        return taskUpdateTitleRequest;
    }

    public static TaskUpdateDescriptionRequest map(UserTaskDescriptionUpdateRequest userTaskDescriptionUpdateRequest) {
        TaskUpdateDescriptionRequest taskUpdateDescriptionRequest = new TaskUpdateDescriptionRequest();
        taskUpdateDescriptionRequest.setTaskId(userTaskDescriptionUpdateRequest.getTaskId());
        taskUpdateDescriptionRequest.setDescription(userTaskDescriptionUpdateRequest.getDescription().toLowerCase());
        return taskUpdateDescriptionRequest;
    }

    public static TaskUpdatePriorityRequest map(UserTaskPriorityUpdateRequest userTaskPriorityUpdateRequest) {
        TaskUpdatePriorityRequest taskUpdatePriorityRequest = new TaskUpdatePriorityRequest();
        taskUpdatePriorityRequest.setPriorityNumber(userTaskPriorityUpdateRequest.getPriorityNumber());
        taskUpdatePriorityRequest.setTaskId(userTaskPriorityUpdateRequest.getTaskId());
        return taskUpdatePriorityRequest;
    }

    public static TaskUpdateDueDateRequest map(UserTaskDueDateUpdateRequest userTaskDueDateUpdateRequest) {
        TaskUpdateDueDateRequest taskUpdateDueDateRequest = new TaskUpdateDueDateRequest();
        taskUpdateDueDateRequest.setTaskId(userTaskDueDateUpdateRequest.getTaskId());
        taskUpdateDueDateRequest.setDueDate(userTaskDueDateUpdateRequest.getDueDate());
        return taskUpdateDueDateRequest;
    }

    public static TaskUpdateStatusRequest map(UserTaskStatusUpdateRequest userTaskStatusUpdateRequest) {
        TaskUpdateStatusRequest taskUpdateStatusRequest = new TaskUpdateStatusRequest();
        taskUpdateStatusRequest.setStatusNumber(userTaskStatusUpdateRequest.getStatusNumber());
        taskUpdateStatusRequest.setTaskId(userTaskStatusUpdateRequest.getTaskId());
        return taskUpdateStatusRequest;
    }

    public static TaskNotificationUpdateRequest map(UserNotificationTimeUpdateRequest userNotificationTimeUpdateRequest) {
        TaskNotificationUpdateRequest taskNotificationUpdateRequest = new TaskNotificationUpdateRequest();
        taskNotificationUpdateRequest.setTaskId(userNotificationTimeUpdateRequest.getTaskId());
        taskNotificationUpdateRequest.setNotificationTime(userNotificationTimeUpdateRequest.getNotificationTime());
        return taskNotificationUpdateRequest;
    }

}
