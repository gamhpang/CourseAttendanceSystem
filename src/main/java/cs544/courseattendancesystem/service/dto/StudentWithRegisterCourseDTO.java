package cs544.courseattendancesystem.service.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class StudentWithRegisterCourseDTO {
    private StudentResponseDTO student;
    private Collection<CourseWithGradeDTO> courses;
}
