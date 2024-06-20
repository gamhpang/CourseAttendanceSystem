package cs544.courseattendancesystem.service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CourseRegistrationDTO {
    private long id;
    private long studentId;
    private String studentName;
    private long courseOfferingId;
    private String grade;
    public CourseRegistrationDTO(){}

    public CourseRegistrationDTO(long id, long studentId, String studentName, long courseOfferingId, String grade) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseOfferingId = courseOfferingId;
        this.grade = grade;
    }
}
