package africa.semicolon.toDoApplication.services;

import africa.semicolon.toDoApplication.exception.InvalidEmailException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Getter
    private String verificationCode;

    public String generateVerificationCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    public void sendVerificationEmail(String userEmail, String username) {
        try {
            InternetAddress emailAddr = new InternetAddress(userEmail);
            emailAddr.validate();
        } catch (AddressException ex) {
            throw new InvalidEmailException("Invalid email address");
        }
        this.verificationCode = generateVerificationCode();
        String formatText = getString(verificationCode, username);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Email Verification");
        message.setText(formatText);

        javaMailSender.send(message);
    }

    public boolean verifyEmail(String userEnteredCode) {
        return verificationCode.equals(userEnteredCode);
    }

    public void sendTaskCreationEmail(String userEmail, String username, String taskTitle, LocalDateTime dueDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Task Created");
        String formattedString = getMailContent(username, taskTitle, dueDate);
        message.setText(formattedString);

        javaMailSender.send(message);
    }

    private static String getMailContent(String username, String taskTitle, LocalDateTime dueDate) {
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

    private static String getString(String verificationCode, String username) {
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
}

