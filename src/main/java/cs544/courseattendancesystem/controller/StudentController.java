package cs544.courseattendancesystem.controller;

import cs544.courseattendancesystem.domain.User;
import cs544.courseattendancesystem.domain.UserRole;
import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.service.StudentService;
import cs544.courseattendancesystem.service.dto.CustomerErrorType;
import cs544.courseattendancesystem.service.dto.StudentDTO;
import cs544.courseattendancesystem.service.dto.StudentResponseDTO;
import cs544.courseattendancesystem.service.dto.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/sys-admin")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/students")
    public ResponseEntity<?> create(@RequestBody StudentDTO studentDTO) {
        try {
            StudentResponseDTO stuDTO = studentService.createStudentByDTO(studentDTO);
            return new ResponseEntity<StudentResponseDTO>(stuDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<CustomerErrorType>(new CustomerErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<CustomerErrorType>(new CustomerErrorType(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<?> getStudent(@PathVariable("studentId") long sutdentId) {
        try {
            StudentResponseDTO student = studentService.getStudentByStudent(sutdentId);
            return new ResponseEntity<StudentResponseDTO>(student, HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            return new ResponseEntity<CustomerErrorType>(new CustomerErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<CustomerErrorType>(new CustomerErrorType(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents() {
        try {
            Collection<StudentResponseDTO> studentList = studentService.getAllStudents();
            return new ResponseEntity<Collection<StudentResponseDTO>>(studentList, HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            return new ResponseEntity<CustomerErrorType>(new CustomerErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<CustomerErrorType>(new CustomerErrorType(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/students/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable("studentId") long studentId, @RequestBody StudentDTO studentDTO) {
        try {
            StudentResponseDTO stuDTO = studentService.updateStudent(studentId, studentDTO);
            return new ResponseEntity<StudentResponseDTO>(stuDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<CustomerErrorType>(new CustomerErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<CustomerErrorType>(new CustomerErrorType(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") long studentId) {
        try {
            studentService.deleteStudent(studentId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<CustomerErrorType>(new CustomerErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<CustomerErrorType>(new CustomerErrorType(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
