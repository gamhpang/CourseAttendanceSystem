package cs544.courseattendancesystem.service;


import cs544.courseattendancesystem.domain.AuditData;
import cs544.courseattendancesystem.domain.Faculty;
import cs544.courseattendancesystem.domain.Student;
import cs544.courseattendancesystem.exception.DuplicateEntryException;
import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.repository.CourseRegistrationRepository;
import cs544.courseattendancesystem.repository.FacultyRepository;
import cs544.courseattendancesystem.repository.StudentRepository;
import cs544.courseattendancesystem.service.adapter.StudentAdapter;
import cs544.courseattendancesystem.service.dto.StudentDTO;
import cs544.courseattendancesystem.service.dto.StudentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;

    @Override
    public StudentResponseDTO createStudentByDTO(StudentDTO studentDTO) {
        Optional<Faculty> faculty = facultyRepository.findById(studentDTO.getFacultyId());
        System.out.println("This is Faculty => " + faculty);
        if (faculty.isEmpty()) {
            throw new ResourceNotFoundException("Faculty not found with Id: " + studentDTO.getFacultyId());
        }
        // Get student from DTO
        Student student = StudentAdapter.getStudentFromStudentDTO(studentDTO);
        try {
            // Set AdviserFaculty
            student.setFaculty(faculty.get());
            // Save student
            studentRepository.save(student);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.isAuthenticated()) {
                AuditData auditData = new AuditData();
                auditData.setCreatedBy(authentication.getName());
                auditData.setCreatedOn(LocalDateTime.now());
                student.setAuditData(auditData);
            }
            System.out.println("Save successfully...");
            return StudentAdapter.getStudentResponseDTOFromStudent(student);
        }catch (DataIntegrityViolationException e){
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new DuplicateEntryException("Duplicate user name entry '" + studentDTO.getUserName());
            } else {
                throw e;
            }
        }
    }

    @Override
    public StudentDTO getStudent(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new ResourceNotFoundException("Student not found with Id: " + id);
        }
        return StudentAdapter.getStudentDTOFromStudent(student.get());
    }

    public StudentResponseDTO getStudentByStudent(long id){
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new ResourceNotFoundException("Student not found with Id: " + id);
        }
        return StudentAdapter.getStudentResponseDTOFromStudent(student.get());
    }

    @Override
    public Student getStudentById(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new ResourceNotFoundException("There are no Student.");
        }
        return student.get();
    }

    @Override
    public StudentResponseDTO updateStudent(long studentId, StudentDTO studentDTO) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Optional<Faculty> faculty = facultyRepository.findById(studentDTO.getFacultyId());
        System.out.println("Optional Student => " + optionalStudent);
        if (optionalStudent.isEmpty()) {
            throw new ResourceNotFoundException("Student not found with Id: " + studentId);
        }
        if (faculty.isEmpty()) {
            throw new ResourceNotFoundException("Faculty not found with Id: " + studentDTO.getFacultyId());
        }
        Student student = optionalStudent.get();
        student.setEntry(studentDTO.getEntry());
        student.setApplicantId(studentDTO.getAlternateId());
        student.setBarCode(studentDTO.getBarCode());
        student.setStudentId(studentDTO.getStudentId());
        student.setApplicantId(studentDTO.getApplicantId());
        student.setFaculty(faculty.get());
        student.setBirthDate(studentDTO.getBirthDate());
        student.setEmailAddress(studentDTO.getEmailAddress());
        student.setGenderType(studentDTO.getGenderType());
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setUserName(studentDTO.getUserName());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            AuditData auditData = student.getAuditData();
            if (auditData != null) {
                auditData.setUpdatedBy(authentication.getName());
                auditData.setUpdatedOn(LocalDateTime.now());
                student.setAuditData(auditData);
            }
        }

        studentRepository.save(student);
        System.out.println("Save Successfully.....");
        return StudentAdapter.getStudentResponseDTOFromStudent(student);
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
        System.out.println("Delete Successfully....");
    }

    @Override
    public Collection<StudentResponseDTO> getAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        return StudentAdapter.getStudentListFromStudentDTO(studentList);
    }

}
