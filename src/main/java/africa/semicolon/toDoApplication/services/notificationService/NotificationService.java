package africa.semicolon.toDoApplication.services.notificationService;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.dtos.NotificationTimeChangeRequest;

import java.time.LocalDateTime;
import java.util.Optional;

public interface NotificationService {

    Notification createNotification(String message);

    void markNotificationAsRead(int id);
    void changeTime(NotificationTimeChangeRequest notificationTimeChangeRequest);

    void save(Notification notification);

    void delete(Notification notification);

    Notification searchNotificationById(int id);
}
