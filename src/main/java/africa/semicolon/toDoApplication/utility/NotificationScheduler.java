package africa.semicolon.toDoApplication.utility;

import africa.semicolon.toDoApplication.datas.models.Task;
import africa.semicolon.toDoApplication.services.EmailService;
import africa.semicolon.toDoApplication.services.notificationService.NotificationService;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
import africa.semicolon.toDoApplication.services.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Component
public class NotificationScheduler {


    @Autowired
    private TaskService taskService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    private final Logger logger = Logger.getLogger(NotificationScheduler.class.getName());


    @Scheduled(cron = "0 * * * * ?")
    public void checkTasksAndSendNotifications() throws InterruptedException {
        List<Task> tasks = taskService.getAllTasks();
        LocalDateTime currentTime = LocalDateTime.now();
        for (Task task : tasks) {
            if (task.getNotification().getTime().isEqual(currentTime) && !task.getNotification().isRead()) {
                userService.sendNotification(task);
                AlarmPlayer.playAlarm();
                logger.info(task.getNotification().getMessage());
                task.getNotification().setRead(true);
                notificationService.save(task.getNotification());
                taskService.save(task);
            }
        }
        Thread.sleep(1000);
    }
}

