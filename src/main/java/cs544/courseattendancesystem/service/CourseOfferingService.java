package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.CourseOfferingType;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;

import java.time.LocalDate;
import java.util.Collection;

public interface CourseOfferingService {
    public CourseOfferingDTO createCourseOffering(double credits, String room, LocalDate startDate, LocalDate endDate,int capacity, Long courseId,Long facultyId,CourseOfferingType courseOfferingType);

    public CourseOfferingDTO getCourseOffering(long courseOfferingId);

    public Collection<CourseOfferingDTO> getAllCourseOfferings();

    public CourseOfferingDTO updateCourseOffering(CourseOfferingDTO courseOfferingDTO);

    public void deleteCourseOffering(long courseOfferingId);
}
