package africa.semicolon.toDoApplication.exception;

public class InvalidEmailException extends TodoApplicationException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
