package africa.semicolon.toDoApplication.datas.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "task_list_table")
public class TaskList {
    @Id
    private int id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Task> tasks;

}
