package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Course;
import cs544.courseattendancesystem.repository.CourseRepository;
import cs544.courseattendancesystem.service.adapter.CourseAdapter;
import cs544.courseattendancesystem.service.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = new Course(courseDTO.getCredits(),courseDTO.getDescription(),courseDTO.getCode(),courseDTO.getName(),courseDTO.getDepartment());
        List<Course> preCourses = new ArrayList<>();
        courseDTO.getPrerequisites().forEach(c->{
            Course tempCourse = courseRepository.findById(c).orElse(null);
            if(tempCourse!=null){
                preCourses.add(tempCourse);
            }
        });
        course.setPrerequisites(preCourses);
        courseRepository.save(course);
        return new CourseAdapter().getCourseDTOFromCourse(course);
    }

    @Override
    public Optional<CourseDTO> getCourse(long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        return course.map(new CourseAdapter()::getCourseDTOFromCourse);
    }

    @Override
    public Collection<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(new CourseAdapter()::getCourseDTOFromCourse).collect(Collectors.toList());
    }

    @Override
    public CourseDTO updateCourse(long courseId,CourseDTO courseDTO) {
        Course course = courseRepository.findById(courseId).orElse(null);
        System.out.println(course);
        if(course != null){
            course.setCredits(courseDTO.getCredits());
            course.setDescription(courseDTO.getDescription());
            course.setCode(courseDTO.getCode());
            course.setName(courseDTO.getName());
            course.setDepartment(courseDTO.getDepartment());
            List<Course> preCourses = new ArrayList<>();
            courseDTO.getPrerequisites().forEach(c->{
                Course tempCourse = courseRepository.findById(c).orElse(null);
                if(tempCourse!=null){
                    preCourses.add(tempCourse);
                }
            });
            course.setPrerequisites(preCourses);
           courseRepository.save(course);
           System.out.println("------------------------------21");
            return new CourseAdapter().getCourseDTOFromCourse(course);
        }
        return null;

    }

    @Override
    public void deleteCourse(long courseId) {
        courseRepository.deleteById(courseId);
    }
}
