package africa.semicolon.toDoApplication.exception;

public class VerificationFailedException extends TodoApplicationException{
    public VerificationFailedException(String message) {
        super(message);
    }
}
