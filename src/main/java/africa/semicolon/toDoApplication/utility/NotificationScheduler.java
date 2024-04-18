package africa.semicolon.toDoApplication.utility;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.services.notificationService.NotificationService;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
import africa.semicolon.toDoApplication.services.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotificationScheduler {


    @Autowired
    private TaskService taskService;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

    @Scheduled(fixedRate = 55000)
    public void checkTasksAndSendNotifications() throws InterruptedException {
        List<Task> tasks = taskService.getAllTasks();
        LocalDateTime currentTime = LocalDateTime.now();
        for (Task task : tasks) {
            if (task.getNotification().getTime().isEqual(currentTime) && !task.getNotification().isRead()) {
                userService.sendNotification(task);
                AlarmPlayer.playAlarm();
                task.getNotification().setRead(true);
                notificationService.save(task.getNotification());
                taskService.save(task);
            }
        }
        Thread.sleep(1000);
    }
}

