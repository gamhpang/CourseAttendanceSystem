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
    private long courseId;
    private long facultyId;
    private List<Long> sessionList;

    public CourseOfferingDTO(long id,double credits, String room, LocalDate startDate, LocalDate endDate, int capacity, CourseOfferingType courseOfferingType,long courseId,long facultyId,List<Long> sessionList) {
        this.credits = credits;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.courseOfferingType = courseOfferingType;
        this.courseId = courseId;
        this.facultyId = facultyId;
        this.sessionList = sessionList;
    }


}
