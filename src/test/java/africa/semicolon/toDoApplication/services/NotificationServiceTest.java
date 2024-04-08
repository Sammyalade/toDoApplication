package africa.semicolon.toDoApplication.services;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.datas.repositories.NotificationRepository;
import africa.semicolon.toDoApplication.dtos.NotificationTimeChangeRequest;
import africa.semicolon.toDoApplication.services.notificationService.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        Notification retrievedNotification = notificationService.searchNotificationById(notification.getId());
        assertThat(retrievedNotification.isRead(), is(true));
        assertThat(notificationRepository.count(), is(1L));
    }

    @Test
    public void createNotification_changeTime_timeIsChangedTest(){
        Notification notification = notificationService.createNotification("message");
        NotificationTimeChangeRequest notificationTimeChangeRequest = new NotificationTimeChangeRequest();
        notificationTimeChangeRequest.setDateTime(LocalDateTime.of(LocalDate.parse("2021-12-12"), LocalTime.parse("09:00")));
        notificationTimeChangeRequest.setId(notification.getId());
        notificationService.changeTime(notificationTimeChangeRequest);
        Notification retrievedNotification = notificationService.searchNotificationById(notification.getId());
        assertThat(retrievedNotification.getTime(), is(LocalDateTime.of(LocalDate.parse("2021-12-12"), LocalTime.parse("09:00"))));
    }
}
