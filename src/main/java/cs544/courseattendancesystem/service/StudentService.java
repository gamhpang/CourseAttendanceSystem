package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.service.dto.StudentDTO;

import java.time.LocalDate;
import java.util.Collection;

public interface StudentService {
    StudentDTO createStudentByDTO(StudentDTO studentDTO);
    StudentDTO getStudent(long id);
    //    StudentDTO updateStudent(Long id, LocalDate birthDate, String emailAddress, String firstName, String lastName, String userName, String password, String entry, long alternateId, long applicantId, long studentId, String barCode);
    StudentDTO updateStudent(long studentId, StudentDTO studentDTO);
    void deleteStudent(long id);
    Collection<StudentDTO> getAllStudents();
}
