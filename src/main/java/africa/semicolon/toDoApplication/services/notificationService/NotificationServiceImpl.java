package africa.semicolon.toDoApplication.services.notificationService;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.datas.repositories.NotificationRepository;
import africa.semicolon.toDoApplication.dtos.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Override
    public void createNotification(NotificationRequest notificationRequest) {
        Notification notification = new Notification();
        notification.setMessage(notificationRequest.getMessage());
        notificationRepository.save(notification);
    }

    @Override
    public void getNotificationForTask(NotificationRequest notificationRequest) {

    }

    @Override
    public void markNotificationAsRead(NotificationRequest notificationRequest) {

    }
}
