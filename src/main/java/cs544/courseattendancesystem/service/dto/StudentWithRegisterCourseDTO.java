package cs544.courseattendancesystem.service.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class StudentWithRegisterCourseDTO {
    private StudentDTO student;
    private Collection<CourseWithGradeDTO> courseWithGradeDTOCollection;
}
