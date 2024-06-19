package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.AttendanceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendConfirmationEmail(String to, String subject, String body){
        //Log the email details
        logger.info("Email To: {}, Subject: {}, Body: {}",to,subject,body);
    }

    public void sendAttendance(List<AttendanceRecord> attendanceRecordList){
        logger.info("Attendance Record Lists->");
        for(AttendanceRecord attendanceRecord:attendanceRecordList){
            logger.info(attendanceRecord.toString());
        }
    }

}
