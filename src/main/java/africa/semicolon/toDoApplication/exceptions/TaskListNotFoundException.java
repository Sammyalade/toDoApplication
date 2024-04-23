package africa.semicolon.toDoApplication.exceptions;

public class TaskListNotFoundException extends TodoApplicationException {
    public TaskListNotFoundException(String message) {
        super(message);
    }
}
