package org.dafe.tripTix.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.dafe.tripTix.entity.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    @Async
    public void send(String to, String email ,String subject ) {
        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("TripTix <admin@neatmanindustrial.com>");

            mailSender.send(message);
        }catch (MessagingException e){

            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");
        }
    }

    public void sendSeatUnblockedEmail(String to, String userName,String seatType, String blockedDate) {
        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("seatType", seatType);
        context.setVariable("blockedDate", blockedDate);
        String emailContent = templateEngine.process("unblock-notification", context);
        send(to, emailContent,"TripTix Notification: Your Blocked Seat is Now Unavailable");
    }

    public void sendSeatBlockedForUserEmail(String to, String userName,String seatType, String blockedDate) {
        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("seatType", seatType);
        context.setVariable("blockedDate", blockedDate);
        String emailContent = templateEngine.process("block-notification", context);
        send(to, emailContent,"TripTix Notification: Your Blocked Seat is Now Unavailable");
    }

    public void sendTripReminderEmail(String to, String userName, Trip trip, String timeFrame, String departureTime) {
        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("trip", trip);
        context.setVariable("departureTime", departureTime);
        context.setVariable("timeFrame", timeFrame);
        String emailContent = templateEngine.process("trip-reminder", context);
        send(to, emailContent, "TripTix Reminder: Your Trip is " + timeFrame);
    }

    public void sendTripTodayReminderEmail(String to, String userName, Trip trip, String departureTime) {
        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("trip", trip);
        context.setVariable("departureTime", departureTime);
        String emailContent = templateEngine.process("trip-today-notification", context);
        send(to, emailContent, "TripTix: Enjoy Your Trip Today!");
    }

    public void sendContactUsNotificationEmail(String to, String fullName, String email, String phoneNumber, String subject, String message) {
        Context context = new Context();
        context.setVariable("fullName", fullName);
        context.setVariable("email", email);
        context.setVariable("phoneNumber", phoneNumber);
        context.setVariable("subject", subject);
        context.setVariable("message", message);
        String emailContent = templateEngine.process("contactus", context);
        send(to, emailContent, "New Contact Us Message Received");
    }

}
