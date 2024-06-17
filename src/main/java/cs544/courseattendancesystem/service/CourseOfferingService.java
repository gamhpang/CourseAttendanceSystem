package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.CourseOfferingType;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;

import java.time.LocalDate;
import java.util.Collection;

public interface CourseOfferingService {
    public CourseOfferingDTO createCourseOffering(CourseOfferingDTO courseOfferingDTO);

    public CourseOfferingDTO getCourseOffering(long courseOfferingId);

    public Collection<CourseOfferingDTO> getAllCourseOfferings();

    public CourseOfferingDTO updateCourseOffering(long courseOfferingId,CourseOfferingDTO courseOfferingDTO);

    public void deleteCourseOffering(long courseOfferingId);
}
