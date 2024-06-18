package cs544.courseattendancesystem.service.adapter;

import cs544.courseattendancesystem.domain.Faculty;
import cs544.courseattendancesystem.domain.Student;
import cs544.courseattendancesystem.repository.FacultyRepository;
import cs544.courseattendancesystem.service.dto.FacultyDTO;
import cs544.courseattendancesystem.service.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;

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
        return student;
    }

    public static List<StudentDTO> getStudentListFromStudentDTO(List<Student> studentList){
        List<StudentDTO> studentDTOList = new ArrayList<StudentDTO>();
        for(Student stu : studentList){
            studentDTOList.add(getStudentDTOFromStudent(stu));
        }
        return studentDTOList;
    }
}
