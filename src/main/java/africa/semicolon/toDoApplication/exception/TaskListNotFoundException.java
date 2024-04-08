package africa.semicolon.toDoApplication.exception;

import africa.semicolon.toDoApplication.exception.TodoApplicationException;

public class TaskListNotFoundException extends TodoApplicationException {
    public TaskListNotFoundException(String message) {
        super(message);
    }
}
