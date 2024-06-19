package cs544.courseattendancesystem.integration.jms;

import cs544.courseattendancesystem.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class EmailReceiver {
    private final Logger logger = LoggerFactory.getLogger(EmailReceiver.class);

    @Autowired
    private EmailService emailService;

    @JmsListener(destination = "emailQueue")
    public void receive(String message) {
        String[] parts = message.split(":", 2);
        String type = parts[0];

        switch (type) {
            case "REMINDER":
                emailService.sendReminderEmail(parts[1]);
                break;
            case "CONFIRMATION":
                String[] confirmationParts = parts[1].split(":", 3);
                String to = confirmationParts[0];
                String subject = confirmationParts[1];
                String body = confirmationParts[2];
                emailService.sendConfirmationEmail(to, subject, body);
                break;
            default:
                logger.warn("Unknown message type received: {}", type);
        }
    }

}
