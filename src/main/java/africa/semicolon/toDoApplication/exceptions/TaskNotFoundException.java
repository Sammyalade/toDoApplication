package africa.semicolon.toDoApplication.exceptions;

public class TaskNotFoundException extends TodoApplicationException {

    public TaskNotFoundException(String message) {
        super(message);
    }
}
