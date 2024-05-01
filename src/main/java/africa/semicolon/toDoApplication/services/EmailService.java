package africa.semicolon.toDoApplication.services;

import africa.semicolon.toDoApplication.exceptions.InvalidEmailException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static africa.semicolon.toDoApplication.utility.EmailMessage.*;

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
        checkEmail(userEmail);
        this.verificationCode = generateVerificationCode();
        String formatText = getString(verificationCode, username);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Email Verification");
        message.setText(formatText);

        javaMailSender.send(message);
    }

    public void sendTaskNotificationEmail(String email, String messageToSend){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Task Notification");
        message.setText(messageToSend);
    }

    public void sendTaskCreatedEmailToNewUser(String assignorUsername, String assigneeUsername, String assigneeEmail){
        checkEmail(assigneeEmail);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(assigneeEmail);
        message.setSubject("Task Created On your Behalf");
        String formattedString = getTaskCreationMail(assigneeUsername, assignorUsername);
        message.setText(formattedString);

        javaMailSender.send(message);
    }

    public void sendYouAssignedTaskEmail(String assignorUsername, String assigneeUsername, String assignorEmail, String taskTitle, LocalDateTime dueDate){
        checkEmail(assignorEmail);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(assignorEmail);
        message.setSubject("Task Assignment Notification");
        String formattedString = sendYouCreatedATaskEmail(assignorUsername, taskTitle, dueDate, assigneeUsername);
        message.setText(formattedString);

        javaMailSender.send(message);
    }

    private static void checkEmail(String assigneeEmail) {
        try {
            InternetAddress emailAddr = new InternetAddress(assigneeEmail);
            emailAddr.validate();
        } catch (AddressException ex) {
            throw new InvalidEmailException("Invalid email address");
        }
    }

    public void sendTaskCreationEmail(String userEmail, String username, String taskTitle, LocalDateTime dueDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Task Created");
        String formattedString = getMailContent(username, taskTitle, dueDate);
        message.setText(formattedString);

        javaMailSender.send(message);
    }
}

