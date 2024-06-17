package cs544.courseattendancesystem;

import cs544.courseattendancesystem.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CourseAttendanceSystemApplication implements CommandLineRunner {

    @Autowired
    FacultyService facultyService;

    public static void main(String[] args) {
        SpringApplication.run(CourseAttendanceSystemApplication.class, args);
    }

    @Override
    public void run(String...args) throws Exception{

        //Creating Faculty
        List<String> hobbies = new ArrayList<>();
        hobbies.add("Listening");
        hobbies.add("Teaching");
        facultyService.createFaculty(LocalDate.of(1999, 10, 31),"john@miu.edu.tt","John","Doe","john","123","Mr.",hobbies);
        facultyService.createFaculty(LocalDate.of(2000, 12, 11),"eddy@miu.edu.tt","Eddy","Doe","eddy","123","Mr.",hobbies);
    }

}
