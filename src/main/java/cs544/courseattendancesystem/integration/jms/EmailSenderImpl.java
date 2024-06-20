package cs544.courseattendancesystem.integration.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs544.courseattendancesystem.domain.AttendanceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailSenderImpl implements EmailSender {

    private static final String EMAIL_QUEUE = "emailQueue";

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(EmailSenderImpl.class);

    @Override
    public void sendConfirmationMessage(String to, String subject, String body) {
        String message = "CONFIRMATION:" + to + ":" + subject + ":" + body;
        System.out.println("Sending confirmation message: " + message);
        jmsTemplate.convertAndSend(EMAIL_QUEUE, message);
    }

    @Override
    public void sendAttendances(String to, List<AttendanceRecord> attendanceRecordList){
        try{
            String message = objectMapper.writeValueAsString(attendanceRecordList);
            jmsTemplate.convertAndSend(to,message);
        }catch (JsonProcessingException e){
            logger.error(e.getMessage());
        }
    }
}
