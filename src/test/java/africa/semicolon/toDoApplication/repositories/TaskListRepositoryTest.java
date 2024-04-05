package africa.semicolon.toDoApplication.repositories;

import africa.semicolon.toDoApplication.datas.models.TaskList;
import africa.semicolon.toDoApplication.datas.repositories.TaskListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class TaskListRepositoryTest {
    @Autowired
    private TaskListRepository taskListRepository;


    @Test
    public void taskListRepositoryTest() {
        TaskList taskList = new TaskList();
        taskListRepository.save(taskList);
        assertThat(taskListRepository.count(), is(1L));
    }
}
