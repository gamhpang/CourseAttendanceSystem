package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.AuditData;
import cs544.courseattendancesystem.domain.Course;
import cs544.courseattendancesystem.repository.CourseRepository;
import cs544.courseattendancesystem.service.adapter.CourseAdapter;
import cs544.courseattendancesystem.service.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService{
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseAdapter courseAdapter;

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()){
            AuditData auditData = new AuditData();
            auditData.setCreatedBy(authentication.getName());
            auditData.setCreatedOn(LocalDateTime.now());
            course.setAuditData(auditData);
        }

        courseRepository.save(course);
        return courseAdapter.getCourseDTOFromCourse(course);
    }

    @Override
    public Optional<CourseDTO> getCourse(long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        return course.map(courseAdapter::getCourseDTOFromCourse);
    }

    @Override
    public Collection<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(courseAdapter::getCourseDTOFromCourse).collect(Collectors.toList());
    }

    @Override
    public CourseDTO updateCourse(long courseId,CourseDTO courseDTO) {
        Course course = courseRepository.findById(courseId).orElse(null);
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

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication.isAuthenticated()){
                AuditData auditData = course.getAuditData();
                if(auditData != null){
                    auditData.setUpdatedBy(authentication.getName());
                    auditData.setUpdatedOn(LocalDateTime.now());
                    course.setAuditData(auditData);
                }
            }

           courseRepository.save(course);
            return courseAdapter.getCourseDTOFromCourse(course);
        }
        return null;

    }

    @Override
    public void deleteCourse(long courseId) {
        courseRepository.deleteById(courseId);
    }
}
