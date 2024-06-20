package cs544.courseattendancesystem.integration.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs544.courseattendancesystem.domain.AttendanceRecord;
import cs544.courseattendancesystem.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
public class EmailReceiver {
    private final Logger logger = LoggerFactory.getLogger(EmailReceiver.class);

    @Autowired
    private EmailService emailService;
    @Autowired
    private ObjectMapper objectMapper;

    @JmsListener(destination = "emailQueue")
    public void receive(String message) {
        String[] confirmationParts = message.split(":", 3);
        String to = confirmationParts[0];
        String subject = confirmationParts[1];
        String body = confirmationParts[2];
        emailService.sendConfirmationEmail(to, subject, body);
    }
    @JmsListener(destination = "attendance_queue")
    public void receiveAttendances(String message) {
        try{
            List<AttendanceRecord> attendanceRecordList = objectMapper.readValue(message,objectMapper.getTypeFactory().constructCollectionType(List.class,AttendanceRecord.class));
            emailService.sendAttendance(attendanceRecordList);
        }catch (JsonProcessingException e){
            logger.error(e.getMessage());
        }
    }

}
