package cs544.courseattendancesystem.controller;

import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.service.AttendanceAndSessionFromOfferingService;
import cs544.courseattendancesystem.service.CourseOfferingService;
import cs544.courseattendancesystem.service.StudentService;
import cs544.courseattendancesystem.service.dto.AttendanceAndSessionFromOfferingDTO;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;
import cs544.courseattendancesystem.service.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cs544.courseattendancesystem.service.CourseRegistrationService;
import cs544.courseattendancesystem.service.dto.CourseWithGradeDTO;
import cs544.courseattendancesystem.service.dto.CourseRegistrationDTO;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/student-view")
public class StudentViewController {
    @Autowired
    private CourseOfferingService courseOfferingService;
    @Autowired
    private StudentService studentService;

    @Autowired
    AttendanceAndSessionFromOfferingService attendanceAndSessionFromOfferingService;

    @GetMapping("/course-offerings/{offeringId}/attendance")
    public ResponseEntity<?> getAttendanceFromCourseOfferings(@RequestHeader long studentId, @PathVariable long offeringId){

        StudentDTO studentDTO = studentService.getStudent(studentId);
        if(studentDTO == null){
            throw new ResourceNotFoundException("Student not found with Id: "+studentId);
        }

        CourseOfferingDTO courseOfferingDTO = courseOfferingService.getCourseOffering(offeringId);
        if(courseOfferingDTO == null){
            throw new ResourceNotFoundException("Course offering not found with Id: "+offeringId);
        }

        AttendanceAndSessionFromOfferingDTO resultDTO= attendanceAndSessionFromOfferingService.getAttendanceAndSession(courseOfferingDTO.getSessionList(),studentId);


        return new ResponseEntity<>(resultDTO, HttpStatus.OK);

    }

    @Autowired
    private CourseRegistrationService courseRegistrationService;
    @GetMapping("/course-offerings")
    public ResponseEntity<List<CourseWithGradeDTO>> getCourseOfferingsWithGrade(@RequestHeader long studentId){
        List<CourseWithGradeDTO> courseWithGradeDTOS = courseRegistrationService.getCourseOfferingWithGradeDTO(studentId);
        return  new ResponseEntity<>(courseWithGradeDTOS, HttpStatus.OK);
    }

    @PostMapping("/course-registrations")
    public ResponseEntity<Void> createCourseRegistration(@RequestBody CourseRegistrationDTO dto) {
        courseRegistrationService.createCourseRegistration(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
