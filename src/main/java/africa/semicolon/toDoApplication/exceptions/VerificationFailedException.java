package africa.semicolon.toDoApplication.exceptions;

public class VerificationFailedException extends TodoApplicationException{
    public VerificationFailedException(String message) {
        super(message);
    }
}
