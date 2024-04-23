package africa.semicolon.toDoApplication.exceptions;

public class EmptyStringException extends TodoApplicationException{
    public EmptyStringException(String message) {
        super(message);
    }
}
