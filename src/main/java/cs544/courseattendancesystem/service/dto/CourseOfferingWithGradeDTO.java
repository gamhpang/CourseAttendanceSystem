package cs544.courseattendancesystem.service.dto;

import cs544.courseattendancesystem.domain.CourseOfferingType;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CourseOfferingWithGradeDTO {
    private long id;
    private double credits;
    private String room;
    private LocalDate startDate;
    private LocalDate endDate;
    private int capacity;
    private CourseOfferingType courseOfferingType;
    private long courseId;
    private long facultyId;
    private String courseName; // From CourseDTO
    private String facultyName; // From FacultyDTO
    private List<Long> sessionList = new ArrayList<>();
    private double grade; // From CourseRegistration

    public CourseOfferingWithGradeDTO(){}

    public CourseOfferingWithGradeDTO(long id, double credits, String room, LocalDate startDate, LocalDate endDate, int capacity, CourseOfferingType courseOfferingType, long courseId, long facultyId, String courseName, String facultyName, List<Long> sessionList, double grade) {
        this.id = id;
        this.credits = credits;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.courseOfferingType = courseOfferingType;
        this.courseId = courseId;
        this.facultyId = facultyId;
        this.courseName = courseName;
        this.facultyName = facultyName;
        this.sessionList = sessionList;
        this.grade = grade;
    }
}

