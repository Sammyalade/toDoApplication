package africa.semicolon.toDoApplication.datas.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="user_table")
public class User {
    @Id
    private int id;
    private String username;
    private String email;
    private boolean isLocked;
    @OneToOne
    private TaskList taskList;

}
