package cs544.courseattendancesystem.controller;

import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.service.AttendanceRecordService;
import cs544.courseattendancesystem.service.CourseOfferingService;
import cs544.courseattendancesystem.service.StudentService;
import cs544.courseattendancesystem.service.dto.AttendanceRecordDTO;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;
import cs544.courseattendancesystem.service.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/student-view")
public class StudentViewController {
    @Autowired
    private CourseOfferingService courseOfferingService;
    @Autowired
    private StudentService studentService;

    @Autowired
    private AttendanceRecordService attendanceRecordService;

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

    @GetMapping("/attendance-records")
    public ResponseEntity<?> getAllAttendanceRecord(@RequestHeader long studentId){
        try{
            System.out.println("Student ID: " + studentId);
            Collection<AttendanceRecordDTO> attendanceRecordDTOS = attendanceRecordService.getAttendanceRecordByStudentId(studentId);
            return new ResponseEntity<Collection<AttendanceRecordDTO>>(attendanceRecordDTOS, HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
