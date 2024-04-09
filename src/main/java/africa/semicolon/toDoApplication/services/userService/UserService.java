package africa.semicolon.toDoApplication.services.userService;

import africa.semicolon.toDoApplication.dtos.request.UserLoginRequest;
import africa.semicolon.toDoApplication.dtos.request.UserRegistrationRequest;
import africa.semicolon.toDoApplication.dtos.request.UserUpdateRequest;
import africa.semicolon.toDoApplication.dtos.response.UserRegistrationResponse;

public interface UserService {

    UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest);
    void loginUser(UserLoginRequest userLoginRequest);
    void logoutUser(int id);
    void updateUser(UserUpdateRequest userUpdateRequest);

    void deleteUser(int id);
}
