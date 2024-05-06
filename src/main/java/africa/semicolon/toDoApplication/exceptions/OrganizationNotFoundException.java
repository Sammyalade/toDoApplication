package africa.semicolon.toDoApplication.exceptions;

public class OrganizationNotFoundException extends TodoApplicationException{
    public OrganizationNotFoundException(String message) {
        super(message);
    }
}
