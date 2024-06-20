package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Faculty;
import cs544.courseattendancesystem.domain.GenderType;
import cs544.courseattendancesystem.domain.Student;
import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.repository.FacultyRepository;
import cs544.courseattendancesystem.repository.StudentRepository;
import cs544.courseattendancesystem.service.adapter.StudentAdapter;
import cs544.courseattendancesystem.service.dto.StudentDTO;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;
    private Faculty faculty;

    @Test
    @WithMockUser
    void createStudentByDTO() {
        // Setup data
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFacultyId(1L);
        studentDTO.setBirthDate(LocalDate.of(1990, 12, 23));
        studentDTO.setEmailAddress("student@test.com");
        studentDTO.setFirstName("John");
        studentDTO.setLastName("Doe");
        studentDTO.setUserName("John Doe");
        studentDTO.setPassword("12345");
        studentDTO.setAlternateId(123456);
        studentDTO.setApplicantId(123456);
        studentDTO.setStudentId(123456);

        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setBirthDate(LocalDate.of(1990, 12, 23));
        faculty.setEmailAddress("faculty@test.com");
        faculty.setFirstName("Jooe");
        faculty.setLastName("Kim");
        faculty.setUserName("Jooe Kim");
        faculty.setPassword("123JK456");
        faculty.setSalutation("Prof");
        faculty.setHobbies(Arrays.asList("Reading", "Swimming"));

        Student student = new Student();
        student.setFaculty(faculty);
        student.setBirthDate(LocalDate.of(1990, 12, 23));
        student.setEmailAddress("student@test.com");
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setUserName("John Doe");
        student.setPassword("123JD456");
        student.setAlternateId(123456);
        student.setApplicantId(123456);
        student.setStudentId(123456);

        // Mock repository methods
        when(facultyRepository.findById(studentDTO.getFacultyId())).thenReturn(Optional.of(faculty));
        when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);

        // Call the method to test
        StudentDTO result = studentService.createStudentByDTO(studentDTO);

        // Verify and assert
        assertNotNull(result); // Ensure that the result is not null
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("student@test.com", result.getEmailAddress());

        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository, times(1)).save(studentCaptor.capture());
        Student capturedStudent = studentCaptor.getValue();

        // Assert that the captured Student has the correct Faculty
        assertNotNull(capturedStudent.getFaculty());
        assertEquals(faculty.getId(), capturedStudent.getFaculty().getId());

        verify(studentRepository, times(1)).save(Mockito.any(Student.class));
    }

    @Test
    void testCreateStudentByNoFaculty(){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFacultyId(1L);
        when(facultyRepository.findById(studentDTO.getFacultyId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            studentService.createStudentByDTO(studentDTO);
        });
    }

    @Test
    void getStudent() {
        long studentId = 1L;

        // Create and set up Faculty object
        Faculty faculty = new Faculty();
        faculty.setId(1L);

        // Create and set up Student object with Faculty
        Student student = new Student();
        student.setFaculty(faculty);
        student.setFirstName("John");
        student.setLastName("Doe");

        // Mock repository methods
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // Call the method to test
        StudentDTO result = studentService.getStudent(studentId);

        // Verify and assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertNotNull(result.getFacultyId());
        assertEquals(1L, result.getFacultyId());

        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    public void testGetStudentNotFound() {
        long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            studentService.getStudent(studentId);
        });
    }

    @Test
    @WithMockUser
    void updateStudent() {
        long studentId = 1L;

        // Create and set up Faculty object
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setUserName("Engineering");

        // Create and set up StudentDTO object
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFacultyId(1L);
        studentDTO.setFirstName("John");
        studentDTO.setLastName("Doe");
        studentDTO.setEntry("Entry");
        studentDTO.setAlternateId(123456);
        studentDTO.setBarCode("Bar123");
        studentDTO.setStudentId(123456);
        studentDTO.setApplicantId(123456);
        studentDTO.setBirthDate(LocalDate.of(2000, 1, 1));
        studentDTO.setEmailAddress("john.doe@example.com");
        studentDTO.setGenderType(GenderType.MALE);
        studentDTO.setUserName("johndoe");

        // Create and set up Student object
        Student student = new Student();
        student.setId(studentId);
        student.setFaculty(faculty);
        student.setFirstName("Jane");
        student.setLastName("Smith");
        student.setEntry("Old Entry");
        student.setApplicantId(987654);
        student.setBarCode("OldBar123");
        student.setStudentId(987654);
        student.setApplicantId(987654);
        student.setBirthDate(LocalDate.of(1999, 1, 1));
        student.setEmailAddress("jane.smith@example.com");
        student.setGenderType(GenderType.FEMALE);
        student.setPassword("oldpassword");
        student.setUserName("janesmith");

        // Mock repository methods
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(facultyRepository.findById(studentDTO.getFacultyId())).thenReturn(Optional.of(faculty));
        when(studentRepository.save(Mockito.any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the method to test
        StudentDTO result = studentService.updateStudent(studentId, studentDTO);

        // Verify and assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("Entry", result.getEntry());
        assertEquals("Bar123", result.getBarCode());
        assertEquals(123456, result.getStudentId());
        assertEquals(123456, result.getApplicantId());
        assertEquals(LocalDate.of(2000, 1, 1), result.getBirthDate());
        assertEquals("john.doe@example.com", result.getEmailAddress());
        assertEquals(GenderType.MALE, result.getGenderType());
        assertEquals("johndoe", result.getUserName());
        assertEquals(1L, result.getFacultyId());

        // Capture the Student object passed to save method
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository, times(1)).save(studentCaptor.capture());
        Student capturedStudent = studentCaptor.getValue();

        // Assert that the captured Student has the correct Faculty
        assertNotNull(capturedStudent.getFaculty());
        assertEquals(faculty.getId(), capturedStudent.getFaculty().getId());
        assertEquals("Engineering", capturedStudent.getFaculty().getUserName());
    }

    @Test
    @WithMockUser
    public void testUpdateStudentNotFound() {
        long studentId = 1L;
        StudentDTO studentDTO = new StudentDTO();
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            studentService.updateStudent(studentId, studentDTO);
        });
    }

    @Test
    void deleteStudent() {
        long studentId = 1L;
        doNothing().when(studentRepository).deleteById(studentId);

        studentService.deleteStudent(studentId);

        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void getAllStudents() {

        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setUserName("Engineering");

        Student student1 = new Student();
        student1.setId(1L);
        student1.setFaculty(faculty);
        student1.setFirstName("Jane");
        student1.setLastName("Smith");
        student1.setBirthDate(LocalDate.of(1999, 1, 1));
        student1.setEmailAddress("jane.smith@example.com");
        student1.setGenderType(GenderType.FEMALE);

        Student student2 = new Student();
        student2.setId(2L);
        student2.setFaculty(faculty);
        student2.setFirstName("Jane");
        student2.setLastName("Smith");
        student2.setBirthDate(LocalDate.of(1999, 1, 1));
        student2.setEmailAddress("jane.smith@example.com");
        student2.setGenderType(GenderType.FEMALE);

        when(studentRepository.findAll()).thenReturn(List.of(student1, student2));

        Collection<StudentDTO> result = studentService.getAllStudents();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findAll();
    }
}