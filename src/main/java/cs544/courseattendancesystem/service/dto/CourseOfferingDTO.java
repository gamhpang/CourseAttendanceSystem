package cs544.courseattendancesystem.service.dto;

import cs544.courseattendancesystem.domain.CourseOfferingType;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CourseOfferingDTO {
    private long id;
    private double credits;
    private String room;
    private LocalDate startDate;
    private LocalDate endDate;
    private int capacity;
    private CourseOfferingType courseOfferingType;
    private CourseDTO course;
    private FacultyDTO faculty;
    private List<SessionDTO> sessionList;

    public CourseOfferingDTO(long id,double credits, String room, LocalDate startDate, LocalDate endDate, int capacity, CourseOfferingType courseOfferingType, CourseDTO course, FacultyDTO faculty) {
        this.credits = credits;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.courseOfferingType = courseOfferingType;
        this.course = course;
        this.faculty = faculty;
        this.sessionList = new ArrayList<>();
    }

    public void addSession(SessionDTO session){
        sessionList.add(session);
    }
}
