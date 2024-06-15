package cs544.courseattendancesystem.controller;

import cs544.courseattendancesystem.service.CourseService;
import cs544.courseattendancesystem.service.dto.CourseDTO;
import cs544.courseattendancesystem.service.dto.CustomerErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/sys-admin")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses(){
        Collection<CourseDTO> courses = courseService.getAllCourses();
        return new ResponseEntity<Collection<CourseDTO>>(courses, HttpStatus.OK);
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable long courseId){
        CourseDTO courseDTO = courseService.getCourse(courseId);
        if(courseDTO==null){
            return new ResponseEntity<CustomerErrorType>(new CustomerErrorType("Course with id= "+courseId+" is not available"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(courseDTO,HttpStatus.OK);
    }

    @PostMapping("/courses")
    public ResponseEntity<?> addCourse(@RequestBody CourseDTO courseDTO){
        CourseDTO cDTO = courseService.createCourse(courseDTO.getCredits(),courseDTO.getDescription(),courseDTO.getCode(),courseDTO.getName(),courseDTO.getDepartment());
        return new ResponseEntity<CourseDTO>(cDTO,HttpStatus.OK);
    }

    @PutMapping("/courses/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable long courseId,CourseDTO courseDTO){
        courseService.updateCourse(courseId,courseDTO);
        return new ResponseEntity<>(courseDTO,HttpStatus.OK);
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable long courseId){
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }














}
