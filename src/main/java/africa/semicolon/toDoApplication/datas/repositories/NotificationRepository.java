package africa.semicolon.toDoApplication.datas.repositories;

import africa.semicolon.toDoApplication.datas.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
