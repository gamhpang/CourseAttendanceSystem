package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.service.dto.CourseOfferingWithDetailsDTO;
import cs544.courseattendancesystem.service.dto.CourseWithGradeDTO;
import cs544.courseattendancesystem.service.dto.CourseRegistrationDTO;

import java.util.Collection;
import java.util.List;

public interface CourseRegistrationService {

    List<CourseWithGradeDTO> getCourseOfferingWithGradeDTO(long studentId);

    void createCourseRegistration(CourseRegistrationDTO dto);

    CourseOfferingWithDetailsDTO getCourseOfferingDetailsWithId(long offeringId);

    Collection<CourseOfferingWithDetailsDTO> getCourseOfferingDetails();
}
