package africa.semicolon.toDoApplication.services.notificationService;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.datas.repositories.NotificationRepository;
import africa.semicolon.toDoApplication.dtos.NotificationUpdateRequest;
import africa.semicolon.toDoApplication.exception.EmptyStringException;
import africa.semicolon.toDoApplication.exception.NotificationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static africa.semicolon.toDoApplication.utility.Utility.IsEmptyOrNullString;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Override
    public Notification createNotification(String message) {
        if(IsEmptyOrNullString(message)) throw new EmptyStringException("Message cannot be null or empty");
        Notification notification = new Notification();
        notification.setMessage(message);
        notificationRepository.save(notification);
        return notification;
    }

    @Override
    public void updateNotification(NotificationUpdateRequest notificationUpdateRequest) {
        Notification notification = searchNotificationById(notificationUpdateRequest.getId());
        if(notificationUpdateRequest.isRead()) notification.setRead(true);
        checkForDateTimeUpdate(notificationUpdateRequest, notification);
        notificationRepository.save(notification);
    }

    private void checkForDateTimeUpdate(NotificationUpdateRequest notificationUpdateRequest, Notification notification) {
        if (notificationUpdateRequest.getTime() != null)
            notification.setTime(LocalDateTime.of(notification.getTime().toLocalDate(), notificationUpdateRequest.getTime()));
        if (notificationUpdateRequest.getDate() != null)
            notification.setTime(LocalDateTime.of(notificationUpdateRequest.getDate(), notification.getTime().toLocalTime()));
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
