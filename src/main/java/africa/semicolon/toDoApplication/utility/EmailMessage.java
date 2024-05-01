package africa.semicolon.toDoApplication.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmailMessage {

    public static String getMailContent(String username, String taskTitle, LocalDateTime dueDate) {
        String taskCreationEmail = """
        Dear %s,

        We are writing to inform you that a new task has been created:

        Task Title: %s
        Due Date: %s

        You can view and manage this task by logging into your ToDoApp account.

        If you have any questions or need further assistance, feel free to reach out to us.

        Best regards,
        ToDoApp Team
        """;
        return String.format(taskCreationEmail, username, taskTitle, dueDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")));
    }

    public static String getString(String verificationCode, String username) {
        String text = """
                Dear %s,
                
                Thank you for registering with ToDoApp. To complete your registration and access all features, we need to verify your email address.
                
                Please use the following verification code to confirm your email address:
                
                Your verification code is: %s
                
                If you did not register for ToDoApp, please disregard this email.
                
                Thank you,
                ToDoApp Team
                """;
        return String.format(text, username, verificationCode);
    }

    public static String getTaskCreationMail(String username, String assignorUsername) {
        String text = """
                Dear %s,
                
                I hope this email finds you well. We're excited to inform you that a task has been created specifically for you by %s! To proceed with this task, we kindly ask you to download our mobile application.
                
                Our mobile app offers a seamless and convenient way for you to access, review, and accept tasks wherever you are. With just a few taps, you'll be able to stay updated on your assignments and streamline your workflow efficiently.
                
                Please follow the instructions below to download our app:
                
                For iOS users:
                Visit the App Store on your iOS device.
                Search for "ToDoApp".
                Tap on "Download" to install the app.
                For Android users:
                Go to the Google Play Store on your Android device.
                Search for "ToDoApp".
                Tap on "Install" to download the app.
                Once you've installed the app, log in using your email, and you'll find the task waiting for you to accept.
                
                If you encounter any issues during the download or login process, please don't hesitate to reach out to our support team on this email. We're here to assist you every step of the way.
                
                Thank you for your cooperation. We look forward to seeing you onboard our platform and collaborating with you on this task.
                
                Best regards,
                
                ToDoApp Team.
                """;

        return String.format(text, username, assignorUsername);

    }

    public static String sendNotificationMessage(String username, String taskTitle, LocalDateTime dueDate){
        String message = """
                Dear %s,
                
                This is to inform you that the following task is due:
                
                Task Name: %s
                Due Date: %s
                
                Please take necessary action.
                
                Best regards,
                Your ToDo Application Team
                """;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        return String.format(message, username, taskTitle, dueDate.format(formatter));
    }

    public static String  sendYouCreatedATaskEmail(String username, String taskTitle, LocalDateTime dueDate, String assignee){
        String message = """
                Dear %s,
                
                I hope you're doing well. I wanted to inform you that a task has been successfully assigned to [Assignee's Name] based on your request.
                
                Task Details:
                
                Task Name: %s
                Assignee: %s
                Due Date: %s
                
                If you have any questions or need further information about this task assignment, please feel free to reach out to me.
                
                Thank you for your attention to this matter.
                
                Best regards,
                Your ToDo Application Team
                """;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        return String.format(message, username, taskTitle, assignee, dueDate.format(formatter));
    }

    public static String sendTaskAssignmentEmail(String username,String taskTitle,LocalDateTime dueDate){
        String message = """
                Dear %s,
                
                I hope this email finds you well. We're excited to inform you that a new task has been assigned to you.
                
                Task Details:
                
                Task Name: %s
                Due Date: %s
                
                You can access this task and manage it through our platform. Please log in to your account to view and track the details.
                
                If you have any questions or need assistance with this task, please don't hesitate to reach out to us.
                
                Thank you for your continued participation!
                
                Best regards,
                Your ToDo Application Team
                """;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        return String.format(message, username, taskTitle, dueDate.format(formatter));
    }
}
