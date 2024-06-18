package cs544.courseattendancesystem.service.dto;

import cs544.courseattendancesystem.domain.CourseOffering;
import cs544.courseattendancesystem.domain.Student;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
public class StudentWithRegisterCourseDTO {
    private StudentDTO student;
    private Collection<CourseWithGradeDTO> courseWithGradeDTOCollection;
}
