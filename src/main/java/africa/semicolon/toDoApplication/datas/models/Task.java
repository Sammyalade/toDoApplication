package africa.semicolon.toDoApplication.datas.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.Table;

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
}
