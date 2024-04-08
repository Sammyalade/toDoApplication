package africa.semicolon.toDoApplication.services.notificationService;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.datas.repositories.NotificationRepository;
import africa.semicolon.toDoApplication.dtos.NotificationTimeChangeRequest;
import africa.semicolon.toDoApplication.exception.NotificationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Notification notification = searchNotificationById(id);
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void changeTime(NotificationTimeChangeRequest notificationTimeChangeRequest) {
        Notification notification = searchNotificationById(notificationTimeChangeRequest.getId());
        notification.setTime(notificationTimeChangeRequest.getDateTime());
        notificationRepository.save(notification);

    }

    @Override
    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public void delete(Notification notification) {
        notificationRepository.delete(notification);
    }

    @Override
    public Notification searchNotificationById(int id){
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isPresent()) {
            return notification.get();
        }
        throw new NotificationNotFoundException("Notification not found");
    }
}
