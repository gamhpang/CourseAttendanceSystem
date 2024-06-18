package cs544.courseattendancesystem.controller;

import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.service.StudentService;
import cs544.courseattendancesystem.service.dto.StudentDTO;
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

    @PostMapping("/students/create")
    public ResponseEntity<?> create(@RequestBody StudentDTO studentDTO){
        try{
            StudentDTO stuDTO = studentService.createStudentByDTO(studentDTO);
            return new ResponseEntity<StudentDTO>(stuDTO, HttpStatus.OK);
        }
        catch (ResourceNotFoundException e){
            System.out.println(e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<?> getStudent(@PathVariable("studentId") long sutdentId){
        StudentDTO student = studentService.getStudent(sutdentId);
        return new ResponseEntity<StudentDTO>(student, HttpStatus.OK);
    }

    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents(){
        Collection<StudentDTO> studentList = studentService.getAllStudents();

        return new ResponseEntity<Collection<StudentDTO>>(studentList, HttpStatus.OK);
    }

    @PutMapping("/students/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable("studentId") long studentId, @RequestBody StudentDTO studentDTO){
        try{
            StudentDTO stuDTO = studentService.updateStudent(studentId, studentDTO);
            return new ResponseEntity<StudentDTO>(stuDTO, HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") long studentId){
        try{
            studentService.deleteStudent(studentId);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
