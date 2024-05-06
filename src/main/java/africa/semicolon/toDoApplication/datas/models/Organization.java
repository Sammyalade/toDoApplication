package africa.semicolon.toDoApplication.datas.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Organization_table")
public class Organization {

    @Id
    @GeneratedValue
    private int id;
    private String Name;
    private String email;
    private String Description;
    @OneToMany
    private List<User> members;
    @OneToMany
    private List<Task> tasks;
}
