package cs544.courseattendancesystem.service.adapter;

import cs544.courseattendancesystem.domain.CourseRegistration;
import cs544.courseattendancesystem.domain.Student;
import cs544.courseattendancesystem.service.dto.StudentWithRegisterCourseDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class StudentWithRegisterCourseAdaptor {
    public StudentWithRegisterCourseDTO getStudentWithRegisterCourseDTOFromCourseRegistration(Collection<CourseRegistration> courseRegistrationDTOCollection, Student stu){
        StudentWithRegisterCourseDTO stuWithRegCourseDTO = new StudentWithRegisterCourseDTO();
        stuWithRegCourseDTO.setStudent(StudentAdapter.getStudentDTOFromStudent(stu));

        return stuWithRegCourseDTO;
    }
}
