package cs544.courseattendancesystem.service.aop;

import cs544.courseattendancesystem.domain.CourseRegistration;
import cs544.courseattendancesystem.service.EmailService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RegistrationConfirmationAdvice {

    @Autowired
    private EmailService emailService;

    @AfterReturning(pointcut = "execution(* cs544.courseattendancesystem.service.CourseRegistrationServiceImpl.createCourseRegistration(..))", returning = "courseRegistration")
    public void sendConfirmationEmail(JoinPoint joinPoint,CourseRegistration courseRegistration){
        emailService.sendConfirmationEmail(courseRegistration.getStudent().getEmailAddress(), "Registration Confirmation", "You have registered successfully");
    }
}
