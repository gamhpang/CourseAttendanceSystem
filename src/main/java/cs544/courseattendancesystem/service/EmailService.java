package cs544.courseattendancesystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendConfirmationEmail(String to, String subject, String body){
        //Log the email details
        logger.info("Email To: {}, Subject: {}, Body: {}",to,subject,body);
    }

    public void sendReminderEmail(String to){
        //Log the reminder details
        logger.info("Sending reminder email to: {}", to);
    }

}
