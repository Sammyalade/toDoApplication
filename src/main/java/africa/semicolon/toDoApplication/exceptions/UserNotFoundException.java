package africa.semicolon.toDoApplication.exceptions;

public class UserNotFoundException extends TodoApplicationException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
