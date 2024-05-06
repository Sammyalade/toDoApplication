package africa.semicolon.toDoApplication.datas.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="user_table")
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String username;
    private String email;
    private boolean isLocked;
    @OneToOne
    private TaskList taskList;
    @OneToOne
    private Organization organization;

}
