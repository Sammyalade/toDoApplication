package africa.semicolon.toDoApplication.exceptions;

public class UserNotLoggedInException extends TodoApplicationException{
    public UserNotLoggedInException(String message) {
        super(message);
    }
}
