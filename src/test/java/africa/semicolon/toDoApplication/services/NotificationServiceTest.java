package africa.semicolon.toDoApplication.services;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.datas.repositories.NotificationRepository;
import africa.semicolon.toDoApplication.dtos.request.NotificationUpdateRequest;
import africa.semicolon.toDoApplication.exceptions.EmptyStringException;
import africa.semicolon.toDoApplication.exceptions.NotificationNotFoundException;
import africa.semicolon.toDoApplication.services.notificationService.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;

    @BeforeEach
    public void setUp() {
        notificationRepository.deleteAll();
    }

    @Test
    public void createNotification_notificationIsCreatedTest() {
        notificationService.createNotification("message");
        assertThat(notificationRepository.count(), is(1L));
    }

    @Test
    public void readNotification_notificationIsReadTest() {
        Notification notification = notificationService.createNotification("message");
        NotificationUpdateRequest notificationUpdateRequest = new NotificationUpdateRequest();
        notificationUpdateRequest.setId(notification.getId());
        notificationUpdateRequest.setRead(true);
        notificationService.updateNotification(notificationUpdateRequest);
        Notification retrievedNotification = notificationService.searchNotificationById(notification.getId());
        assertThat(retrievedNotification.isRead(), is(true));
        assertThat(notificationRepository.count(), is(1L));
    }

    @Test
    public void createNotification_changeTime_timeIsChangedTest(){
        Notification notification = notificationService.createNotification("message");
        notification.setTime(LocalDateTime.now());
        notificationService.save(notification);
        NotificationUpdateRequest notificationUpdateRequest = new NotificationUpdateRequest();
        notificationUpdateRequest.setTime(LocalTime.parse("09:00"));
        notificationUpdateRequest.setDate(LocalDate.parse("2021-12-12"));
        notificationUpdateRequest.setId(notification.getId());
        notificationService.updateNotification(notificationUpdateRequest);
        Notification retrievedNotification = notificationService.searchNotificationById(notification.getId());
        assertThat(retrievedNotification.getTime(), is(LocalDateTime.of(LocalDate.parse("2021-12-12"), LocalTime.parse("09:00"))));
    }

    @Test
    public void testThatNotificationTimeAndDateIsSet(){
        Notification notification = notificationService.createNotification("message");
        notification.setTime(LocalDateTime.of(LocalDate.parse("2024-04-17"), LocalTime.parse("09:00")));
        notificationService.save(notification);
        assertThat(notification.getTime(), is(LocalDateTime.of(LocalDate.parse("2024-04-17"), LocalTime.parse("09:00"))));
    }

    @Test
    public void searchForNotificationThatDoesNotExistTest(){
        assertThatThrownBy(() -> {
                    notificationService.searchNotificationById(11);
                })
                .isInstanceOf(NotificationNotFoundException.class)
                .hasMessageContaining("Notification not found");
    }

    @Test
    public void updateNotificationThatDoesNotExist_throwsExceptionTest(){
        NotificationUpdateRequest notificationUpdateRequest = new NotificationUpdateRequest();
        notificationUpdateRequest.setTime(LocalTime.parse("09:00"));
        notificationUpdateRequest.setDate(LocalDate.parse("2021-12-12"));
        notificationUpdateRequest.setId(11);
        assertThatThrownBy(()->{
            notificationService.updateNotification(notificationUpdateRequest);
        })
                .isInstanceOf(NotificationNotFoundException.class)
                .hasMessageContaining("Notification not found");
    }

    @Test
    public void createNotificationWithEmptyString_throwsExceptionTest(){
        assertThatThrownBy(()->{
            notificationService.createNotification("");
        })
                .isInstanceOf(EmptyStringException.class)
                .hasMessageContaining("Message cannot be null or empty");
    }
    @Test
    public void createNotificationWithNull_throwsExceptionTest(){
        assertThatThrownBy(()->{
            notificationService.createNotification(null);
        })
                .isInstanceOf(EmptyStringException.class)
                .hasMessageContaining("Message cannot be null or empty");
    }

}
