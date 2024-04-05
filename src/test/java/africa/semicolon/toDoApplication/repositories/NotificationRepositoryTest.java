package africa.semicolon.toDoApplication.repositories;

import africa.semicolon.toDoApplication.datas.models.Notification;
import africa.semicolon.toDoApplication.datas.repositories.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;


    @Test
    public void notificationRepositoryTest() {
        Notification notification = new Notification();
        notificationRepository.save(notification);
        assertThat(notificationRepository.count(), is(1L));
    }
}
