package cs544.courseattendancesystem.service.adapter;

import cs544.courseattendancesystem.domain.Course;
import cs544.courseattendancesystem.service.dto.CourseDTO;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter {
    public static Course getCourseFromCourseDTO(CourseDTO courseDTO){
        Course course = new Course(courseDTO.getCredits(),courseDTO.getDescription(),courseDTO.getCode(),courseDTO.getName(),courseDTO.getDepartment());
        List<Course> courses = new ArrayList<>();
        courseDTO.getPrerequisites().forEach(dto -> {
            courses.add(CourseAdapter.getCourseFromCourseDTO(dto));
        });

        course.setPrerequisites(courses);
        return course;
    }

    public static CourseDTO getCourseDTOFromCourse(Course course){
        CourseDTO courseDTO = new CourseDTO(course.getId(),course.getCredits(),course.getDescription(),course.getCode(),course.getName(),course.getDepartment());
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (Course c : course.getPrerequisites()) {
            courseDTOS.add(getCourseDTOFromCourse(c));
        }
        courseDTO.setPrerequisites(courseDTOS);

        return courseDTO;
    }
}
