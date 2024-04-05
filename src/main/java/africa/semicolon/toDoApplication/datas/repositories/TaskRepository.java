package africa.semicolon.toDoApplication.datas.repositories;

import africa.semicolon.toDoApplication.datas.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
