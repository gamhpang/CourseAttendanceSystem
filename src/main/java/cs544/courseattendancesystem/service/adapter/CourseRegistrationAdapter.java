package cs544.courseattendancesystem.service.adapter;

import cs544.courseattendancesystem.domain.CourseRegistration;
import cs544.courseattendancesystem.service.dto.CourseRegistrationDTO;


public class CourseRegistrationAdapter {

    public  static CourseRegistrationDTO toDTO(CourseRegistration courseRegistration) {
        if (courseRegistration == null) {
            return null;
        }

        CourseRegistrationDTO dto = new CourseRegistrationDTO();
        dto.setId(courseRegistration.getId());
        dto.setStudentId(courseRegistration.getStudent().getId());
        dto.setStudentName(courseRegistration.getStudent().getFirstName() + " " + courseRegistration.getStudent().getFirstName()); // Assuming Student has a getName() method
        dto.setCourseOfferingId(courseRegistration.getCourseOffering().getId());
        dto.setGrade(courseRegistration.getGrade());
        dto.setRegistrationEndDate(courseRegistration.getRegistrationEndDate());
        return dto;
    }
}
