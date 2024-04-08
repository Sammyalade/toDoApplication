package africa.semicolon.toDoApplication.exception;

public class TaskNotFoundException extends TodoApplicationException {

    public TaskNotFoundException(String message) {
        super(message);
    }
}
