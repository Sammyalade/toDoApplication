package africa.semicolon.toDoApplication.repositories;

import africa.semicolon.toDoApplication.datas.models.Organization;
import africa.semicolon.toDoApplication.datas.repositories.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class OrganizationRepositoryTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    public void testOrganizationRepository() {
        Organization organization = new Organization();
        organizationRepository.save(organization);
        assertThat(organizationRepository.count(), is(1L));
    }
}
