package africa.semicolon.toDoApplication.datas.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notification_table")
public class Notification {
    @Id
    private int id;
    private String message;
    private LocalDateTime time = LocalDateTime.now();
    private boolean isRead;
}
