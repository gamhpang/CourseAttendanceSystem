package cs544.courseattendancesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CourseAttendanceSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseAttendanceSystemApplication.class, args);
    }
}
