package africa.semicolon.toDoApplication.services;

import africa.semicolon.toDoApplication.datas.models.Organization;
import africa.semicolon.toDoApplication.datas.models.User;
import africa.semicolon.toDoApplication.datas.repositories.OrganizationRepository;
import africa.semicolon.toDoApplication.dtos.request.AddMemberRequest;
import africa.semicolon.toDoApplication.dtos.request.OrganizationLoginRequest;
import africa.semicolon.toDoApplication.dtos.request.OrganizationUpdateRequest;
import africa.semicolon.toDoApplication.dtos.request.RemoveMemberRequest;
import africa.semicolon.toDoApplication.services.organizationService.OrganizationService;
import africa.semicolon.toDoApplication.services.taskService.TaskService;
import africa.semicolon.toDoApplication.services.userService.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@Transactional
public class OrganizationServiceTest {


    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationRepository organizationRepository;

    @BeforeEach
    public void setUp() {
        organizationRepository.deleteAll();
    }

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

    @Test
    public void createUser_createOrganization_updateOrganizationName_nameIsUpdatedTest(){
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
        OrganizationUpdateRequest request = new OrganizationUpdateRequest();
        request.setId(organization.getId());
        request.setString("NewName");
        organizationService.updateOrganizationName(request);
        assertThat(organizationService.searchForOrganization(organization.getId()).getName(), is("NewName"));
    }


    @Test
    public void createUser_createOrganization_updateOrganizationDescription_descriptionIsUpdatedTest(){
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
        OrganizationUpdateRequest request = new OrganizationUpdateRequest();
        request.setId(organization.getId());
        request.setString("NewDescription");
        organizationService.updateOrganizationDescription(request);
        assertThat(organizationService.searchForOrganization(organization.getId()).getDescription(),
                is("NewDescription"));
    }



    @Test
    public void createUser_createOrganization_addNewMember_memberIsAddedTest(){
        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@admin.com");
        user.setLocked(true);
        userService.save(user);
        User user1 = new User();
        userService.save(user1);
        Organization organization = new Organization();
        organization.setName("Name");
        organization.setDescription("Description");
        organization.setMembers(new ArrayList<>());
        organization.getMembers().add(user);
        user.setOrganization(organization);
        organizationRepository.save(organization);
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setUserId(user1.getId());
        addMemberRequest.setOrganizationId(organization.getId());
        organizationService.addMoreMember(addMemberRequest);
        assertThat(organizationService.searchForOrganization(organization.getId()).getMembers().size(), is(2));
    }


    @Test
    public void createUser_createOrganization_addNewMember_removeMember_memberIsRemovedTest(){
        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@admin.com");
        user.setLocked(true);
        userService.save(user);
        User user1 = new User();
        userService.save(user1);
        Organization organization = new Organization();
        organization.setName("Name");
        organization.setDescription("Description");
        organization.setMembers(new ArrayList<>());
        organization.getMembers().add(user);
        user.setOrganization(organization);
        organizationRepository.save(organization);
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setUserId(user1.getId());
        addMemberRequest.setOrganizationId(organization.getId());
        organizationService.addMoreMember(addMemberRequest);
        RemoveMemberRequest request = new RemoveMemberRequest();
        request.setOrganizationId(organization.getId());
        request.setUserId(user1.getId());
        organizationService.removeMember(request);
        assertThat(organizationService.searchForOrganization(organization.getId()).getMembers().size(), is(2));
    }




}
