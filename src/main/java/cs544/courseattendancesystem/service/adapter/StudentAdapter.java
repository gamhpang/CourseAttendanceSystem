package cs544.courseattendancesystem.service.adapter;

import cs544.courseattendancesystem.domain.Faculty;
import cs544.courseattendancesystem.domain.Student;
import cs544.courseattendancesystem.domain.User;
import cs544.courseattendancesystem.domain.UserRole;
import cs544.courseattendancesystem.repository.FacultyRepository;
import cs544.courseattendancesystem.service.dto.FacultyDTO;
import cs544.courseattendancesystem.service.dto.StudentDTO;
import cs544.courseattendancesystem.service.dto.StudentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentAdapter {
    public static StudentDTO getStudentDTOFromStudent(Student student){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setEntry(student.getEntry());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setUserName(student.getUserName());
        studentDTO.setAlternateId(student.getAlternateId());
        studentDTO.setBarCode(student.getBarCode());
        studentDTO.setStudentId(student.getStudentId());
        studentDTO.setApplicantId(student.getApplicantId());
        studentDTO.setFacultyId(student.getFaculty().getId());
        studentDTO.setFaculty(FacultyAdapter.getFacultyDTOFromFaculty(student.getFaculty()));
        studentDTO.setBirthDate(student.getBirthDate());
        studentDTO.setEmailAddress(student.getEmailAddress());
        studentDTO.setGenderType(student.getGenderType());
        return studentDTO;
    }

    public static StudentResponseDTO getStudentResponseDTOFromStudent(Student student){
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
        studentResponseDTO.setId(student.getId());
        studentResponseDTO.setEntry(student.getEntry());
        studentResponseDTO.setStudentName(student.getFirstName() + " " + student.getLastName());
        studentResponseDTO.setUserName(student.getUserName());
        studentResponseDTO.setBirthDate(student.getBirthDate());
        studentResponseDTO.setEmailAddress(student.getEmailAddress());
        studentResponseDTO.setGender(student.getGenderType().toString());
        studentResponseDTO.setAdviserName(FacultyAdapter.getFacultyDTOFromFaculty(student.getFaculty()).getFirstName() + " " + FacultyAdapter.getFacultyDTOFromFaculty(student.getFaculty()).getLastName());
        studentResponseDTO.setAlternateId(student.getAlternateId());
        studentResponseDTO.setBarCode(student.getBarCode());
        studentResponseDTO.setStudentId(student.getStudentId());
        studentResponseDTO.setApplicantId(student.getApplicantId());
        return studentResponseDTO;
    }

    public static Student getStudentFromStudentDTO(StudentDTO studentDTO){
        Student student = new Student();
        student.setEntry(studentDTO.getEntry());
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setUserName(studentDTO.getUserName());
        student.setAlternateId(studentDTO.getAlternateId());
        student.setBarCode(studentDTO.getBarCode());
        student.setStudentId(studentDTO.getStudentId());
        student.setApplicantId(studentDTO.getApplicantId());
        student.setBirthDate(studentDTO.getBirthDate());
        student.setEmailAddress(studentDTO.getEmailAddress());
        student.setGenderType(studentDTO.getGenderType());
        User user = new User();
        user.setUsername(studentDTO.getUserName());
        user.setPassword(new BCryptPasswordEncoder().encode(studentDTO.getPassword()));
        user.setUserRole(UserRole.STUDENT);
        student.setUser(user);
        return student;
    }

    public static List<StudentResponseDTO> getStudentListFromStudentDTO(List<Student> studentList){
        List<StudentResponseDTO> studentResponseDTOs = new ArrayList<StudentResponseDTO>();
        for(Student stu : studentList){
            studentResponseDTOs.add(getStudentResponseDTOFromStudent(stu));
        }
        return studentResponseDTOs;
    }
}
