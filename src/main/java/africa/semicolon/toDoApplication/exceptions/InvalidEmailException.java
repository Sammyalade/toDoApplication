package africa.semicolon.toDoApplication.exceptions;

public class InvalidEmailException extends TodoApplicationException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
