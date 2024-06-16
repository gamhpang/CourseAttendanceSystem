package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.service.dto.CourseDTO;

import java.util.Collection;

public interface CourseService {
    public CourseDTO createCourse(CourseDTO courseDTO);
    public CourseDTO getCourse(long courseId);
    public Collection<CourseDTO> getAllCourses();
    public CourseDTO updateCourse(long courseId,CourseDTO courseDTO);
    public void deleteCourse(long courseId);
}
