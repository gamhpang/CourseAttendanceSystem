package cs544.courseattendancesystem.controller;

import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.service.CourseOfferingService;
import cs544.courseattendancesystem.service.StudentService;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;
import cs544.courseattendancesystem.service.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student-view")
public class StudentViewController {
    @Autowired
    private CourseOfferingService courseOfferingService;
    @Autowired
    private StudentService studentService;
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

        return null;

    }
}
