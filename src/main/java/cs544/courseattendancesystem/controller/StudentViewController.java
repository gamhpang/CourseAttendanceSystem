package cs544.courseattendancesystem.controller;

import cs544.courseattendancesystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student-view")
public class StudentViewController {

    @Autowired
    private StudentService studentService;

}
