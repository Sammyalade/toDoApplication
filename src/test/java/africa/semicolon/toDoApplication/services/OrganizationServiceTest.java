package africa.semicolon.toDoApplication.services;

import africa.semicolon.toDoApplication.datas.models.Organization;
import africa.semicolon.toDoApplication.datas.models.User;
import africa.semicolon.toDoApplication.datas.repositories.OrganizationRepository;
import africa.semicolon.toDoApplication.dtos.request.OrganizationLoginRequest;
import africa.semicolon.toDoApplication.services.organizationService.OrganizationService;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
import africa.semicolon.toDoApplication.services.userService.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class OrganizationServiceTest {


    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    public void createOrganization_addAUser_organizationIsCreatedTest(){
        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@admin.com");
        userService.save(user);
        Organization organization = new Organization();
        organization.setName("Name");
        organization.setDescription("Description");
        organization.setMembers(new ArrayList<>());
        organization.getMembers().add(user);
        user.setOrganization(organization);
        organizationRepository.save(organization);
        assertThat(organizationRepository.count(), is(1L));
    }

    @Test
    public void createOrganization_loginAsAUser_userIsLoggedInTest(){
        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@admin.com");
        user.setLocked(true);
        userService.save(user);
        Organization organization = new Organization();
        organization.setName("Name");
        organization.setDescription("Description");
        organization.setMembers(new ArrayList<>());
        organization.getMembers().add(user);
        user.setOrganization(organization);
        organizationRepository.save(organization);
        OrganizationLoginRequest request = new OrganizationLoginRequest();
        request.setUserId(user.getId());
        request.setEmail(user.getEmail());
        organizationService.login(request);
        assertThat(userService.searchUserById(user.getId()).isLocked(), is(false));
    }

    @Test
    public void createUser_loginUser_logoutUser_userIsLoggedOutTest(){
        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@admin.com");
        user.setLocked(true);
        userService.save(user);
        Organization organization = new Organization();
        organization.setName("Name");
        organization.setDescription("Description");
        organization.setMembers(new ArrayList<>());
        organization.getMembers().add(user);
        user.setOrganization(organization);
        organizationRepository.save(organization);
        OrganizationLoginRequest request = new OrganizationLoginRequest();
        request.setUserId(user.getId());
        request.setEmail(user.getEmail());
        organizationService.login(request);
        organizationService.logout(user.getId());
        assertThat(userService.searchUserById(user.getId()).isLocked(), is(true));
    }
}
