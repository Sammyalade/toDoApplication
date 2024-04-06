package africa.semicolon.toDoApplication.services;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.datas.repositories.NotificationRepository;
import africa.semicolon.toDoApplication.dtos.NotificationRequest;
import africa.semicolon.toDoApplication.services.notificationService.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setMessage("Message");
        notificationService.createNotification(notificationRequest);
        assertThat(notificationRepository.count(), is(1L));
    }
}
