package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.CourseOffering;
import cs544.courseattendancesystem.domain.CourseOfferingType;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;

import java.time.LocalDate;
import java.util.Collection;

public class CourseOfferingServiceImpl implements CourseOfferingService {

    @Override
    public CourseOfferingDTO createCourseOffering(double credits, String room, LocalDate startDate, LocalDate endDate, int capacity, Long courseId, Long facultyId, CourseOfferingType courseOfferingType) {
        CourseOffering courseOffering = new CourseOffering(credits,room,startDate,endDate,capacity,courseOfferingType);
        return null;
    }

    @Override
    public CourseOfferingDTO getCourseOffering(long courseOfferingId) {
        return null;
    }

    @Override
    public Collection<CourseOfferingDTO> getAllCourseOfferings() {
        return null;
    }

    @Override
    public CourseOfferingDTO updateCourseOffering(CourseOfferingDTO courseOfferingDTO) {
        return null;
    }

    @Override
    public void deleteCourseOffering(long courseOfferingId) {

    }
}
