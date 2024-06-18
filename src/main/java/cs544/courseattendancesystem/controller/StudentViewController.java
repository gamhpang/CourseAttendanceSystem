package cs544.courseattendancesystem.controller;

import cs544.courseattendancesystem.service.CourseRegistrationService;
import cs544.courseattendancesystem.service.dto.CourseWithGradeDTO;
import cs544.courseattendancesystem.service.dto.CourseRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student-view")
public class StudentViewController {

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
