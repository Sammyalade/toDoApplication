package africa.semicolon.toDoApplication.utility;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.dtos.TaskCreationRequest;

public class Mapper {

    public static Task map(TaskCreationRequest taskCreationRequest) {
        Task task = new Task();
        task.setTitle(taskCreationRequest.getTitle());
        task.setDescription(taskCreationRequest.getDescription());
        task.setStatus(taskCreationRequest.getStatus());
        task.setNotification(taskCreationRequest.getNotification());
        return task;
    }
}
