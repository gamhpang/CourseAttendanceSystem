package cs544.courseattendancesystem.service.dto;

import cs544.courseattendancesystem.domain.CourseOfferingType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseWithGradeDTO {

    private long courseId; // From CourseDTO
    private String courseName; // From CourseDTO
    private String courseCode; // From CourseDTO
    private String courseDepartment; // From CourseDTO
    private long facultyId; // From FacultyDTO
    private String facultyName; // From FacultyDTO
    private String grade; // From CourseRegistration
    private int capacity;
    private long courseOfferingId;
    private double credits;
    private String room;
    private CourseOfferingType courseOfferingType;
    private LocalDate startDate;
    private LocalDate endDate;

    public CourseWithGradeDTO(){}

}

