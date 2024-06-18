package cs544.courseattendancesystem.service.dto;

import cs544.courseattendancesystem.domain.CourseOfferingType;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CourseOfferingWithDetailsDTO {

    private LocalDate startDate;
    private LocalDate endDate;
    private long courseOfferingId;
    private String room;
    private int capacity;
    private CourseOfferingType courseOfferingType;
    private long courseId;
    private String courseName;
    private String courseCode;
    private double credits;
    private String courseDepartment;
    private long facultyId;
    private String facultyName;
    private List<CourseRegistrationDTO> registrations = new ArrayList<>();

    public CourseOfferingWithDetailsDTO() {}

    public CourseOfferingWithDetailsDTO(long courseOfferingId, double credits, String room, LocalDate startDate, LocalDate endDate, int capacity, CourseOfferingType courseOfferingType, long courseId, String courseName, String courseCode, String courseDepartment, long facultyId, String facultyName,List<CourseRegistrationDTO> registrations) {
        this.courseOfferingId = courseOfferingId;
        this.credits = credits;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.courseOfferingType = courseOfferingType;
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.courseDepartment = courseDepartment;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.registrations = registrations;
    }
}

