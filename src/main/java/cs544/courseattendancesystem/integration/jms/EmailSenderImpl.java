package cs544.courseattendancesystem.integration.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderImpl implements EmailSender {

    private static final String EMAIL_QUEUE = "emailQueue";

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendReminderMessage(String to) {
        String message = "REMINDER:" + to;
        System.out.println("Sending reminder message: " + message);
        jmsTemplate.convertAndSend(EMAIL_QUEUE, message);
    }

    @Override
    public void sendConfirmationMessage(String to, String subject, String body) {
        String message = "CONFIRMATION:" + to + ":" + subject + ":" + body;
        System.out.println("Sending confirmation message: " + message);
        jmsTemplate.convertAndSend(EMAIL_QUEUE, message);
    }
}
