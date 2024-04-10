package africa.semicolon.toDoApplication.utility;

import africa.semicolon.toDoApplication.dtos.request.TaskNotificationTimeChangeRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static LocalDateTime mergeDateAndTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }

    public static boolean isEmptyOrNullString(String title) {
        return title == null || title.isEmpty();
    }

    public static TaskNotificationTimeChangeRequest map(int id, LocalDate date){
        TaskNotificationTimeChangeRequest taskNotificationTimeChangeRequest = new TaskNotificationTimeChangeRequest();
        taskNotificationTimeChangeRequest.setId(id);
        taskNotificationTimeChangeRequest.setTime(date);
        return taskNotificationTimeChangeRequest;
    }

    public static List<?> checkIfListIsNull(List<?> lists) {
        if(lists == null) lists = new ArrayList<>();

        return lists;
    }
}
