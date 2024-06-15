package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Course;
import cs544.courseattendancesystem.repository.CourseRepository;
import cs544.courseattendancesystem.service.adapter.CourseAdapter;
import cs544.courseattendancesystem.service.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public CourseDTO createCourse(double credits, String description, String code, String name, String department) {
        Course course = new Course(credits,description,code,name,department);
        courseRepository.save(course);
        return CourseAdapter.getCourseDTOFromCourse(course);
    }

    @Override
    public CourseDTO getCourse(long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        return course.map(CourseAdapter::getCourseDTOFromCourse).orElse(null);
    }

    @Override
    public Collection<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(CourseAdapter::getCourseDTOFromCourse).collect(Collectors.toList());
    }

    @Override
    public CourseDTO updateCourse(long courseId,CourseDTO courseDTO) {
        Course course = courseRepository.save(CourseAdapter.getCourseFromCourseDTO(courseDTO));
        return CourseAdapter.getCourseDTOFromCourse(course);
    }

    @Override
    public void deleteCourse(long courseId) {
        courseRepository.deleteById(courseId);
    }
}
