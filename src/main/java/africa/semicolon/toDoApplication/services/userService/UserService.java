package africa.semicolon.toDoApplication.services.userService;

import africa.semicolon.toDoApplication.datas.models.User;
import africa.semicolon.toDoApplication.dtos.*;

import java.util.List;

public interface UserService {

    void registerUser(UserRegistrationRequest userRegistrationRequest);
    void loginUser(UserLoginRequest userLoginRequest);
    void logoutUser(String username);
    void updateUser(UserUpdateRequest userUpdateRequest);
    void deleteUser(UserDeleteRequest userDeleteRequest);
    void changePassword(UserPasswordChangeRequest userPasswordChangeRequest);
    void resetPassword(UserPasswordResetRequest userPasswordResetRequest);
    void searchUser(String username);
    List<User> getUsers();
}
