package africa.semicolon.toDoApplication.exception;

public class UserNotLoggedInException extends TodoApplicationException{
    public UserNotLoggedInException(String message) {
        super(message);
    }
}
