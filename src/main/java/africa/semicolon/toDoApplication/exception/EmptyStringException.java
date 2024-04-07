package africa.semicolon.toDoApplication.exception;

public class EmptyStringException extends TodoApplicationException{
    public EmptyStringException(String message) {
        super(message);
    }
}
