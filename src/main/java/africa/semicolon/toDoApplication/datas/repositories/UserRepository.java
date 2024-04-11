package africa.semicolon.toDoApplication.datas.repositories;

import africa.semicolon.toDoApplication.datas.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}
