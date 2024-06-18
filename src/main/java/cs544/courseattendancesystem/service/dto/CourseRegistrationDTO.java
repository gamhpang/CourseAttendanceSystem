package cs544.courseattendancesystem.service.dto;

import lombok.Data;

@Data
public class CourseRegistrationDTO {
    private long id;
    private long studentId;
    private String studentName;
    private long courseOfferingId;
    private String grade;
    public CourseRegistrationDTO(){}
}
