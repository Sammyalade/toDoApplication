package africa.semicolon.toDoApplication.services.notificationService;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.datas.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Override
    public Notification createNotification(String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notificationRepository.save(notification);
        return notification;
    }


    @Override
    public void markNotificationAsRead(int id) {
        findById(id).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }



    @Override
    public Optional<Notification> findById(int id) {
        return notificationRepository.findById(id);
    }

    @Override
    public void changeTime(LocalDateTime time) {

    }
}
