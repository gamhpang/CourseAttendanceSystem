package cs544.courseattendancesystem.controller;


import cs544.courseattendancesystem.service.CourseOfferingService;
import cs544.courseattendancesystem.service.FacultyService;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;
import cs544.courseattendancesystem.service.dto.CustomerErrorType;
import cs544.courseattendancesystem.service.dto.FacultyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/sys-admin")
public class CourseOfferingController {
    @Autowired
    private CourseOfferingService courseOfferingService;

    @Autowired
    private FacultyService facultyService;

    @GetMapping("/course-offerings")
    private ResponseEntity<?> getAllCourseOfferings(){
        Collection<CourseOfferingDTO> courseOfferingDTOS = courseOfferingService.getAllCourseOfferings();
        return new ResponseEntity<Collection<CourseOfferingDTO>>(courseOfferingDTOS, HttpStatus.OK);
    }

    @GetMapping("/course-offerings/{courseOfferingId}")
    private ResponseEntity<?> getCourseOfferingById(@PathVariable long courseOfferingId){
        CourseOfferingDTO courseOfferingDTO = courseOfferingService.getCourseOffering(courseOfferingId);
        if(courseOfferingDTO == null){
            return new ResponseEntity<CustomerErrorType>(new CustomerErrorType("CourseOffering with id= "+courseOfferingId+" is not available"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(courseOfferingDTO,HttpStatus.OK);
    }

    @PostMapping("/course-offerings")
    private ResponseEntity<?> createCourseOffering(@RequestBody CourseOfferingDTO courseOfferingDTO){
        CourseOfferingDTO coDTO = courseOfferingService.createCourseOffering(courseOfferingDTO);
        return new ResponseEntity<CourseOfferingDTO>(coDTO,HttpStatus.OK);
    }

    @PutMapping("/course-offerings/{courseOfferingId}")
    public ResponseEntity<?> updateCourseOffering(@PathVariable long courseOfferingId,@RequestBody CourseOfferingDTO courseOfferingDTO){
        CourseOfferingDTO coDTO = courseOfferingService.updateCourseOffering(courseOfferingId,courseOfferingDTO);
        return new ResponseEntity<CourseOfferingDTO>(coDTO,HttpStatus.OK);
    }

    @DeleteMapping("/course-offerings/{courseOfferingId}")
    public  ResponseEntity<?> deleteCourseOffering(@PathVariable long courseOfferingId){
        CourseOfferingDTO courseOfferingDTO = courseOfferingService.getCourseOffering(courseOfferingId);
        if(courseOfferingDTO == null){
            return new ResponseEntity<CustomerErrorType>(new CustomerErrorType("CourseOffering with id= "+courseOfferingId+" is not available"),HttpStatus.NOT_FOUND);
        }
        courseOfferingService.deleteCourseOffering(courseOfferingId);
        return new ResponseEntity<>(courseOfferingDTO,HttpStatus.OK);
    }

    @PostMapping("/faculty")
    public ResponseEntity<?> createFaculty(@RequestBody FacultyDTO facultyDTO){
        FacultyDTO fDTO = facultyService.createFaculty(facultyDTO);
        return new ResponseEntity<>(fDTO,HttpStatus.OK);
    }
}
