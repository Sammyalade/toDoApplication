package africa.semicolon.toDoApplication.services.notificationService;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.dtos.NotificationUpdateRequest;

public interface NotificationService {

    Notification createNotification(String message);

    void updateNotification(NotificationUpdateRequest notificationUpdateRequest);

    void save(Notification notification);

    void delete(Notification notification);

    Notification searchNotificationById(int id);
}
