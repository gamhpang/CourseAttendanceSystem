package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Student;
import cs544.courseattendancesystem.service.dto.StudentDTO;
import cs544.courseattendancesystem.service.dto.StudentResponseDTO;

import java.time.LocalDate;
import java.util.Collection;

public interface StudentService {
//    StudentDTO createStudentByDTO(StudentDTO studentDTO);
    StudentResponseDTO createStudentByDTO(StudentDTO studentDTO);
    StudentDTO getStudent(long id);
    StudentResponseDTO getStudentByStudent(long id);
    Student getStudentById(long id);
    StudentResponseDTO updateStudent(long studentId, StudentDTO studentDTO);
    void deleteStudent(long id);
    Collection<StudentResponseDTO> getAllStudents();
}
