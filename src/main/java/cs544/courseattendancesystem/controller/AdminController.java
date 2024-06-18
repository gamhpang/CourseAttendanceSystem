package cs544.courseattendancesystem.controller;

import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.service.AttendanceRecordService;
import cs544.courseattendancesystem.service.CourseOfferingService;
import cs544.courseattendancesystem.service.CourseRegistrationService;
import cs544.courseattendancesystem.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/admin-view")
public class AdminController {

    @Autowired
    private CourseRegistrationService courseRegistrationService;
    @Autowired
    private CourseOfferingService courseOfferingService;
    @Autowired
    private AttendanceRecordService attendanceRecordService;

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

    @GetMapping("/course-offerings/{offeringId}/attendance")
    private ResponseEntity<?> getAttendanceByOfferingId(@PathVariable long offeringId){
        CourseOfferingDTO courseOfferingDTO = courseOfferingService.getCourseOffering(offeringId);
        if(courseOfferingDTO == null){
            throw new ResourceNotFoundException("Course offering not found with Id: "+offeringId);
        }
        List<AttendanceRecordDTO> resultDTO = new ArrayList<>();
        for(Long sessionId:courseOfferingDTO.getSessionList()){
            List<AttendanceRecordDTO> attendanceRecordDTOS = attendanceRecordService.getAttendanceRecordDTOBySessionId(sessionId);
            resultDTO.addAll(attendanceRecordDTOS);
        }

        return new ResponseEntity<>(resultDTO,HttpStatus.OK);
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
