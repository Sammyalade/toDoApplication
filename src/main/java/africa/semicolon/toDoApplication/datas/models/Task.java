package africa.semicolon.toDoApplication.datas.models;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="task_table")
public class Task {
    @Id
    private int id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Status status;
    @OneToOne
    private Notification notification;

}
