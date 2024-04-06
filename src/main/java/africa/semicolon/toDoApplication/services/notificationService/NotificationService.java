package africa.semicolon.toDoApplication.services.notificationService;

import africa.semicolon.toDoApplication.dtos.NotificationRequest;

public interface NotificationService {

    void sendNotificationForTask(NotificationRequest notificationRequest);
    void getNotificationForTask(NotificationRequest notificationRequest);
    void markNotificationAsRead(NotificationRequest notificationRequest);

}
