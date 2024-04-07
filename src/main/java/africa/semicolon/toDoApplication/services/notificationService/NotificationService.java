package africa.semicolon.toDoApplication.services.notificationService;

import africa.semicolon.toDoApplication.datas.models.Notification;

import java.util.Optional;

public interface NotificationService {

    Notification createNotification(String message);

    void markNotificationAsRead(int id);

    Optional<Notification> findById(int id);
}
