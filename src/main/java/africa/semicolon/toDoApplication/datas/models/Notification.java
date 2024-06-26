package africa.semicolon.toDoApplication.datas.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notification_table")
public class Notification {
    @Id
    @GeneratedValue
    private int id;
    private String message;
    private LocalDateTime time;
    private boolean isRead;
}
