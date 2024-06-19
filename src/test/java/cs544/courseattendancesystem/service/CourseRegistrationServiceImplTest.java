package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Course;
import cs544.courseattendancesystem.domain.CourseOffering;
import cs544.courseattendancesystem.domain.CourseOfferingType;
import cs544.courseattendancesystem.domain.CourseRegistration;
import cs544.courseattendancesystem.domain.Faculty;
import cs544.courseattendancesystem.domain.Student;
import cs544.courseattendancesystem.repository.CourseOfferingRepository;
import cs544.courseattendancesystem.repository.CourseRegistrationRepository;
import cs544.courseattendancesystem.repository.StudentRepository;
import cs544.courseattendancesystem.service.dto.CourseOfferingWithDetailsDTO;
import cs544.courseattendancesystem.service.dto.CourseWithGradeDTO;
import cs544.courseattendancesystem.service.dto.CourseRegistrationDTO;
import cs544.courseattendancesystem.service.dto.StudentWithRegisterCourseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CourseRegistrationServiceImplTest {

    @TestConfiguration
    static class CourseRegistrationServiceImplTestContextConfiguration {
        @Bean
        public CourseRegistrationService courseRegistrationService() {
            return new CourseRegistrationServiceImpl();
        }
    }

    @Autowired
    private CourseRegistrationService courseRegistrationService;

    @Autowired
    private StudentService studentService;

    @MockBean
    private CourseRegistrationRepository courseRegistrationRepository;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private CourseOfferingRepository courseOfferingRepository;

    private Student student;
    private CourseOffering courseOffering;
    private CourseRegistration courseRegistration;
    private CourseRegistrationDTO courseRegistrationDTO;
    private List<CourseRegistration> courseRegistrationList;
    private List<CourseWithGradeDTO> courseWithGradeDTOList;
    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");

        courseOffering = new CourseOffering(3, "Room 101", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 12, 1), 30, CourseOfferingType.ON_CAMPUS);
        Course course = new Course(3, "Description", "C-1", "Course 1", "Department 1");
        courseOffering.setCourse(course);
        courseOffering.setId(1L);
        Faculty faculty = new Faculty(LocalDate.of(1999, 10, 31), "john@miu.edu.tt", "John", "Doe", "john", "123", "tt", Arrays.asList("Listening", "Reading"));
        courseOffering.setFaculty(faculty);
        student.setFaculty(faculty);

        courseRegistration = new CourseRegistration("A");
        courseRegistration.setId(1L);
        courseRegistration.setStudent(student);
        courseRegistration.setCourseOffering(courseOffering);

        courseRegistrationDTO = new CourseRegistrationDTO();
        courseRegistrationDTO.setStudentId(student.getId());
        courseRegistrationDTO.setCourseOfferingId(courseOffering.getId());
        courseRegistrationDTO.setGrade("A");

        // Initialize lists
        courseRegistrationList = new ArrayList<>();
        courseRegistrationList.add(courseRegistration);

        courseWithGradeDTOList = new ArrayList<>();
        CourseWithGradeDTO courseWithGradeDTO = new CourseWithGradeDTO();
        courseWithGradeDTO.setCourseName("Course 1");
        courseWithGradeDTO.setGrade("A");
        courseWithGradeDTOList.add(courseWithGradeDTO);
    }

    @Test
    void testGetCourseOfferingWithGradeDTO() {
        when(courseRegistrationRepository.findByStudentId(1L)).thenReturn(Arrays.asList(courseRegistration));

        List<CourseWithGradeDTO> result = courseRegistrationService.getCourseOfferingWithGradeDTO(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(courseOffering.getCourse().getCode(), result.get(0).getCourseCode());
        verify(courseRegistrationRepository, times(1)).findByStudentId(1L);
    }

    @Test
    void testCreateCourseRegistration_Success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseOfferingRepository.findById(1L)).thenReturn(Optional.of(courseOffering));
        when(courseRegistrationRepository.save(any(CourseRegistration.class))).thenReturn(courseRegistration);

        courseRegistrationService.createCourseRegistration(courseRegistrationDTO);

        verify(courseRegistrationRepository, times(1)).save(any(CourseRegistration.class));
    }

    @Test
    void testCreateCourseRegistration_StudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            courseRegistrationService.createCourseRegistration(courseRegistrationDTO);
        });

        assertEquals("Student not found", exception.getMessage());
    }

    @Test
    void testCreateCourseRegistration_CourseOfferingNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseOfferingRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            courseRegistrationService.createCourseRegistration(courseRegistrationDTO);
        });

        assertEquals("Course offering not found", exception.getMessage());
    }

    @Test
    void testGetCourseOfferingDetailsWithId_Success() {
        when(courseOfferingRepository.findById(1L)).thenReturn(Optional.of(courseOffering));
        when(courseRegistrationRepository.findByCourseOfferingId(1L)).thenReturn(Arrays.asList(courseRegistration));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        CourseOfferingWithDetailsDTO result = courseRegistrationService.getCourseOfferingDetailsWithId(1L);

        assertNotNull(result);
        assertEquals(courseOffering.getId(), result.getCourseOfferingId());
        verify(courseOfferingRepository, times(1)).findById(1L);
        verify(courseRegistrationRepository, times(1)).findByCourseOfferingId(1L);
    }

    @Test
    void testGetCourseOfferingDetailsWithId_CourseOfferingNotFound() {
        when(courseOfferingRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            courseRegistrationService.getCourseOfferingDetailsWithId(1L);
        });

        assertEquals("Course offering not found", exception.getMessage());
    }

    @Test
    void testGetCourseOfferingDetails() {
        when(courseOfferingRepository.findAll()).thenReturn(Arrays.asList(courseOffering));
        when(courseRegistrationRepository.findByCourseOfferingId(1L)).thenReturn(Arrays.asList(courseRegistration));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Collection<CourseOfferingWithDetailsDTO> result = courseRegistrationService.getCourseOfferingDetails();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(courseOfferingRepository, times(1)).findAll();
        verify(courseRegistrationRepository, times(1)).findByCourseOfferingId(1L);
    }

    @Test
    void testGetCourseOfferingByStudent() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(courseRegistrationRepository.findByStudentId(anyLong())).thenReturn(courseRegistrationList);

        StudentWithRegisterCourseDTO result = courseRegistrationService.getCourseOfferingByStudent(1L);

        assertNotNull(result);
        assertNotNull(result.getStudent());
        assertEquals("John", result.getStudent().getFirstName());
        assertEquals("Doe", result.getStudent().getLastName());
        assertTrue(result.getCourseWithGradeDTOCollection().size() > 0);
        assertEquals("Course 1", result.getCourseWithGradeDTOCollection().iterator().next().getCourseName());
        assertEquals("A", result.getCourseWithGradeDTOCollection().iterator().next().getGrade());
    }

}

