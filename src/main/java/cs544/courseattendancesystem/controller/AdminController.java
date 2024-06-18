package cs544.courseattendancesystem.controller;

import cs544.courseattendancesystem.service.CourseRegistrationService;
import cs544.courseattendancesystem.service.dto.CourseOfferingWithDetailsDTO;
import cs544.courseattendancesystem.service.dto.CustomerErrorType;
import cs544.courseattendancesystem.service.dto.StudentWithRegisterCourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/admin-view")
public class AdminController {

    @Autowired
    private CourseRegistrationService courseRegistrationService;

    @GetMapping("/course-offerings")
    private ResponseEntity<?> getAllCourseOfferings(){
        Collection<CourseOfferingWithDetailsDTO> courseOfferingWithDetailsDTO = courseRegistrationService.getCourseOfferingDetails();
        return new ResponseEntity<Collection<CourseOfferingWithDetailsDTO>>(courseOfferingWithDetailsDTO, HttpStatus.OK);
    }

    @GetMapping("/course-offerings/{courseOfferingId}")
    private ResponseEntity<?> getCourseOfferingById(@PathVariable long courseOfferingId){
        CourseOfferingWithDetailsDTO courseOfferingWithDetailsDTO = courseRegistrationService.getCourseOfferingDetailsWithId(courseOfferingId);
        if(courseOfferingWithDetailsDTO == null){
            return new ResponseEntity<CustomerErrorType>(new CustomerErrorType("CourseOffering with id= "+courseOfferingId+" is not available"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(courseOfferingWithDetailsDTO,HttpStatus.OK);
    }


    @GetMapping("/students/{studentId}")
    public ResponseEntity<?> getCourseOfferingByStudent(@PathVariable long studentId){
        StudentWithRegisterCourseDTO studentWithRegisterCourseDTO = courseRegistrationService.getCourseOfferingByStudent(studentId);
        if(studentWithRegisterCourseDTO == null){
            return new ResponseEntity<CustomerErrorType>(new CustomerErrorType("CourseOffering with Student id= "+studentId+" is not available"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentWithRegisterCourseDTO,HttpStatus.OK);
    }
}
