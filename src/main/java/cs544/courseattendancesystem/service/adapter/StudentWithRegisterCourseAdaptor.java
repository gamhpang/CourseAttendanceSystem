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

//        if (!courseRegistrationDTOCollection.isEmpty()) {
//            Collection<CourseRegistrationDTO> courseRegistrationDTOCollection1 = new ArrayList<>();
//            Iterator<CourseRegistration> iterator = courseRegistrationDTOCollection.iterator();
//
//            while (iterator.hasNext()){
//                stuWithRegCourseDTO.get
//                courseRegistrationDTOCollection1.add(iterator.next().getCourseOffering());
//            }
//        } else {
//            System.out.println("Collection is empty");
//        }
        return stuWithRegCourseDTO;
    }
}
