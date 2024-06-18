package cs544.courseattendancesystem.service.adapter;

import cs544.courseattendancesystem.domain.Course;
import cs544.courseattendancesystem.repository.CourseRepository;
import cs544.courseattendancesystem.service.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CourseAdapter {
    @Autowired
    public CourseRepository courseRepository;
    public Course getCourseFromCourseDTO(CourseDTO courseDTO){
        if(courseDTO==null){
            return null;
        }
        //Course course = new Course(courseDTO.getCredits(),courseDTO.getDescription(),courseDTO.getCode(),courseDTO.getName(),courseDTO.getDepartment());
        Course course = courseRepository.findById(courseDTO.getId()).orElse(null);
        List<Course> courses = new ArrayList<>();
        courseDTO.getPrerequisites().forEach(dto -> {
            Course temp = courseRepository.findById(dto).orElse(null);
            if(temp!=null){
                courses.add(temp);
            }
        });
        course.setPrerequisites(courses);
        return course;
    }

    public CourseDTO getCourseDTOFromCourse(Course course){
        if(course == null){
            return null;
        }
        CourseDTO courseDTO = new CourseDTO(course.getId(),course.getCredits(),course.getDescription(),course.getCode(),course.getName(),course.getDepartment());
        List<Long> courseDTOS = new ArrayList<>();
        for (Course c : course.getPrerequisites()) {
            courseDTOS.add(c.getId());
        }
        courseDTO.setPrerequisites(courseDTOS);

        return courseDTO;
    }
}
