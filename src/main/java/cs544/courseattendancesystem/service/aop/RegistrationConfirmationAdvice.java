package cs544.courseattendancesystem.service.aop;

import cs544.courseattendancesystem.domain.CourseRegistration;
import cs544.courseattendancesystem.service.EmailService;
import cs544.courseattendancesystem.service.StudentService;
import cs544.courseattendancesystem.service.dto.CourseRegistrationDTO;
import cs544.courseattendancesystem.service.dto.StudentDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.annotations.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RegistrationConfirmationAdvice {

    @Autowired
    private EmailService emailService;
    @Autowired
    private StudentService studentService;

    @AfterReturning(pointcut = "execution(* cs544.courseattendancesystem.service.CourseRegistrationServiceImpl.createCourseRegistration(..))", returning = "courseRegistration")
    public void sendConfirmationEmail(JoinPoint joinPoint, CourseRegistrationDTO courseRegistration){
        System.out.println("-------------123");
        StudentDTO dto = studentService.getStudent(courseRegistration.getStudentId());
        emailService.sendConfirmationEmail(dto.getEmailAddress(), "Registration Confirmation", "You have registered successfully");
    }
}
