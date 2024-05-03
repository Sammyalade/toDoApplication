package africa.semicolon.toDoApplication.datas.repositories;

import africa.semicolon.toDoApplication.datas.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
