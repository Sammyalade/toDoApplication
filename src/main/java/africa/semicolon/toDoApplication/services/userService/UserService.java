package africa.semicolon.toDoApplication.services.userService;

import africa.semicolon.toDoApplication.datas.models.User;
import africa.semicolon.toDoApplication.dtos.*;
import africa.semicolon.toDoApplication.dtos.response.UserRegistrationResponse;

import java.util.List;

public interface UserService {

    UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest);
    void loginUser(UserLoginRequest userLoginRequest);
    void logoutUser(int id);
    void updateUser(UserUpdateRequest userUpdateRequest);

    void deleteUser(int id);
}
