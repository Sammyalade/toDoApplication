package africa.semicolon.toDoApplication.datas.repositories;

import africa.semicolon.toDoApplication.datas.models.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
}
