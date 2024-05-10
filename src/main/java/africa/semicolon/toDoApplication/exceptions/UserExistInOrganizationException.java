package africa.semicolon.toDoApplication.exceptions;

public class UserExistInOrganizationException extends TodoApplicationException{
    public UserExistInOrganizationException(String message) {
        super(message);
    }
}
