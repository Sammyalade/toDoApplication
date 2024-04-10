package africa.semicolon.toDoApplication.exception;

public class UserNotFoundException extends TodoApplicationException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
