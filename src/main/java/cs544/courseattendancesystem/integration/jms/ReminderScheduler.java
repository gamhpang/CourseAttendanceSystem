package cs544.courseattendancesystem.integration.jms;

import cs544.courseattendancesystem.repository.CourseRegistrationRepository;
import cs544.courseattendancesystem.repository.StudentRepository;
import cs544.courseattendancesystem.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReminderScheduler {

    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = 3000)
    public void sendReminders(){

    }
}
