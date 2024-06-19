package cs544.courseattendancesystem.integration.jms;

import cs544.courseattendancesystem.domain.AttendanceRecord;
import cs544.courseattendancesystem.domain.CourseOffering;
import cs544.courseattendancesystem.repository.AttendanceRecordRepository;
import cs544.courseattendancesystem.repository.CourseOfferingRepository;
import cs544.courseattendancesystem.repository.CourseRegistrationRepository;
import cs544.courseattendancesystem.repository.StudentRepository;
import cs544.courseattendancesystem.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReminderScheduler {

    @Autowired
    private EmailSender emailSender;
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    //@Scheduled(fixedRate = 3000)
    @Scheduled(cron="0 0 23 * * ?")
    public void sendAttendanceListAtTheEndOfClass(){
        LocalDate today = LocalDate.now();
        List<CourseOffering> courseOfferings = courseOfferingRepository.findAllWithEndDateToday(today);
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();

        for(CourseOffering co:courseOfferings){
            co.getSessionList().forEach(session -> {
                List<AttendanceRecord> attRecords = attendanceRecordRepository.getAttendanceRecordBySessionId(session.getId());
                if(!attRecords.isEmpty()){
                    attendanceRecords.addAll(attRecords);
                }
            });
        }
        emailSender.sendAttendances("attendance_queue",attendanceRecords);
    }
}
