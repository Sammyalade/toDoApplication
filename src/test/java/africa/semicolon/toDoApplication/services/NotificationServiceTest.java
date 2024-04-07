package africa.semicolon.toDoApplication.services;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.datas.repositories.NotificationRepository;
import africa.semicolon.toDoApplication.services.notificationService.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    public void createNotification_notificationIsCreatedTest() {
        notificationService.createNotification("message");
        assertThat(notificationRepository.count(), is(1L));
    }

    @Test
    public void readNotification_notificationIsReadTest() {
        Notification notification = notificationService.createNotification("message");
        notificationService.markNotificationAsRead(notification.getId());
        Optional<Notification> retrievedNotification = notificationService.findById(notification.getId());
        Notification readNotification = retrievedNotification.get();
        assertThat(readNotification.isRead(), is(true));
        assertThat(notificationRepository.count(), is(1L));
    }
}
