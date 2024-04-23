package africa.semicolon.toDoApplication.exceptions;

public class EmailAlreadyRegisteredException extends TodoApplicationException{
    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}
