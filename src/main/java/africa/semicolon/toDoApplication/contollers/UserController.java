package africa.semicolon.toDoApplication.contollers;

import africa.semicolon.toDoApplication.dtos.request.UserRegistrationRequest;
import africa.semicolon.toDoApplication.dtos.response.UserApiResponse;
import africa.semicolon.toDoApplication.dtos.response.UserRegistrationResponse;
import africa.semicolon.toDoApplication.exception.TodoApplicationException;
import africa.semicolon.toDoApplication.services.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        try{
            UserRegistrationResponse response = userService.registerUser(userRegistrationRequest);
            return new ResponseEntity<>(new UserApiResponse(true, response), CREATED);
        }
        catch(TodoApplicationException e){
            return new ResponseEntity<>(new UserApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
}
