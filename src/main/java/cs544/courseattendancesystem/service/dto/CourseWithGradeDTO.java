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

    public CourseWithGradeDTO(long courseId, String courseName, String courseCode, String courseDepartment, long facultyId, String facultyName, String grade, int capacity, long courseOfferingId, double credits, String room, CourseOfferingType courseOfferingType, LocalDate startDate, LocalDate endDate) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.courseDepartment = courseDepartment;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.grade = grade;
        this.capacity = capacity;
        this.courseOfferingId = courseOfferingId;
        this.credits = credits;
        this.room = room;
        this.courseOfferingType = courseOfferingType;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

