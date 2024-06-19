package cs544.courseattendancesystem.service;


import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;
import cs544.courseattendancesystem.service.dto.CourseOfferingWithDetailsDTO;

import java.time.LocalDate;
import java.util.Collection;

public interface CourseOfferingService {
    CourseOfferingDTO createCourseOffering(CourseOfferingDTO courseOfferingDTO);

    CourseOfferingDTO getCourseOffering(long courseOfferingId);

//    CourseOfferingWithDetailsDTO getCourseOfferingWithDetails(long courseOfferingId);

    Collection<CourseOfferingDTO> getAllCourseOfferings();

    Collection<CourseOfferingDTO> getAllCourseOfferingsByDate(LocalDate reqDate);

    CourseOfferingDTO updateCourseOffering(long courseOfferingId, CourseOfferingDTO courseOfferingDTO);

    void deleteCourseOffering(long courseOfferingId);
}
