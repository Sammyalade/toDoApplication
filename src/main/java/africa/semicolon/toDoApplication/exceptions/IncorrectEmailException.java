package africa.semicolon.toDoApplication.exceptions;

public class IncorrectEmailException extends TodoApplicationException{
    public IncorrectEmailException(String message) {
        super(message);
    }
}
