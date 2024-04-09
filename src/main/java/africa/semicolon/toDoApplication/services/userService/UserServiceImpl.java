package africa.semicolon.toDoApplication.services.userService;

import africa.semicolon.toDoApplication.datas.models.User;
import africa.semicolon.toDoApplication.datas.repositories.UserRepository;
import africa.semicolon.toDoApplication.dtos.request.UserLoginRequest;
import africa.semicolon.toDoApplication.dtos.request.UserRegistrationRequest;
import africa.semicolon.toDoApplication.dtos.request.UserUpdateRequest;
import africa.semicolon.toDoApplication.dtos.response.UserRegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static africa.semicolon.toDoApplication.utility.Mapper.map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest) {
        User user = map(userRegistrationRequest);
        userRepository.save(user);
        return map(user);
    }

    @Override
    public void loginUser(UserLoginRequest userLoginRequest) {
        Optional<User> user = userRepository.findById(userLoginRequest.getId());
        if (user.isPresent()) {
            User user1 = user.get();
            if(user1.getEmail().equals(userLoginRequest.getEmail())){
                user1.setLocked(false);
            }
        }

    }

    @Override
    public void logoutUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setLocked(true);
        }
    }

    @Override
    public void updateUser(UserUpdateRequest userUpdateRequest) {
        Optional<User> user = userRepository.findById(userUpdateRequest.getId());
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setEmail(userUpdateRequest.getEmail());
        }
    }

    @Override
    public void deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> userRepository.delete(value));
    }
}
