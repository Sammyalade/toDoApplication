package africa.semicolon.toDoApplication.exception;

public class EmailAlreadyRegisteredException extends TodoApplicationException{
    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}
