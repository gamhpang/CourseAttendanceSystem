package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.*;
import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.repository.CourseOfferingRepository;
import cs544.courseattendancesystem.service.adapter.CourseAdapter;
import cs544.courseattendancesystem.service.adapter.CourseOfferingAdapter;
import cs544.courseattendancesystem.service.dto.CourseDTO;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CourseOfferingServiceImplTest {

    @TestConfiguration
    static class CourseOfferingServiceImplTestContextConfiguration{
        // Define a bean for the service under test
        @Bean
        public CourseOfferingServiceImpl courseOfferingService(){
            return new CourseOfferingServiceImpl();
        }
    }

    // Autowire the service to be tested
    @Autowired
    private CourseOfferingService courseOfferingService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private FacultyService facultyService;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private CourseOfferingRepository courseOfferingRepository;

    @MockBean
    private CourseAdapter courseAdapter;

    @MockBean
    private CourseOfferingAdapter courseOfferingAdapter;

    //Define the test data
    private CourseOfferingDTO courseOfferingDTO;
    private CourseOffering courseOffering;
    private CourseDTO courseDTO;
    private Course course;
    private Faculty faculty;
    private List<Session> sessions;

    private AuditData auditData;

    @BeforeEach
    public void setUp() {
        // Initialize test data before each test
        courseOfferingDTO = new CourseOfferingDTO(1L, 3, "Room 101", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 12, 1), 30, CourseOfferingType.ON_CAMPUS, 1L, 1L, new ArrayList<>());
        courseOffering = new CourseOffering(3, "Room 101", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 12, 1), 30, CourseOfferingType.ON_CAMPUS);
        courseDTO = new CourseDTO(1L, 3, "Description", "C-1", "Course 1", "Department 1");
        course = new Course(3, "Description", "C-1", "Course 1", "Department 1");
        faculty = new Faculty(LocalDate.of(1999, 10, 31),"john@miu.edu.tt","John","Doe","john","123","tt",Arrays.asList("Listening","Reading"));
        sessions = Arrays.asList(
                new Session(LocalDate.of(2023, 6, 1), LocalTime.of(10, 0), LocalTime.of(12, 30)),
                new Session(LocalDate.of(2023, 6, 1), LocalTime.of(13, 30), LocalTime.of(15, 30)),
                new Session(LocalDate.of(2023, 6, 2), LocalTime.of(10, 0), LocalTime.of(12, 30))
        );
        List<Long> sessionIds = Arrays.asList(1L,2L,3L);
        courseOfferingDTO.setSessionList(sessionIds);
    }

    @Test
    @WithMockUser
    void testCreateCourseOffering_Success() {
        //Define behaviour
        when(courseService.getCourse(courseOfferingDTO.getCourseId())).thenReturn(Optional.of(courseDTO));
        when(courseAdapter.getCourseFromCourseDTO(courseDTO)).thenReturn(course);
        when(facultyService.getFaculty(courseOfferingDTO.getFacultyId())).thenReturn(faculty);
        when(sessionService.generateSessions(courseOfferingDTO.getStartDate(), courseOfferingDTO.getEndDate())).thenReturn(sessions);
        when(courseOfferingRepository.save(any(CourseOffering.class))).thenReturn(courseOffering);
        when(courseOfferingAdapter.getCourseOfferingDTOFromCourseOffering(courseOffering)).thenReturn(courseOfferingDTO);

        // Call the service method and assert the result
        CourseOfferingDTO result = courseOfferingService.createCourseOffering(courseOfferingDTO);

        // Ensure the result is not null
        assertNotNull(result);
        // Ensure the result matches the expected DTO
        assertEquals(courseOfferingDTO, result);
        // Verify the save method was called once
        verify(courseOfferingRepository, times(1)).save(any(CourseOffering.class));
    }

    @Test
    void testCreateCourseOffering_CourseNotFound() {
        //Define behaviour
        when(courseService.getCourse(courseOfferingDTO.getCourseId())).thenReturn(Optional.empty());

        // Call the service method and assert the exception
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            courseOfferingService.createCourseOffering(courseOfferingDTO);
        });

        assertEquals("Course not found with Id: 1", exception.getMessage());
    }

    @Test
    void testGetCourseOffering_Success() {
        //Define behavior
        when(courseOfferingRepository.findById(1L)).thenReturn(Optional.of(courseOffering));
        when(courseOfferingAdapter.getCourseOfferingDTOFromCourseOffering(courseOffering)).thenReturn(courseOfferingDTO);

        //Call the service method and assert the result
        CourseOfferingDTO result = courseOfferingService.getCourseOffering(1L);

        assertNotNull(result);
        assertEquals(courseOfferingDTO, result);
    }

    @Test
    void testGetCourseOffering_NotFound() {
        when(courseOfferingRepository.findById(1L)).thenReturn(Optional.empty());

        CourseOfferingDTO result = courseOfferingService.getCourseOffering(1L);

        assertNull(result);
    }

    @Test
    void testGetAllCourseOfferings() {
        List<CourseOffering> courseOfferings = Arrays.asList(courseOffering);
        List<CourseOfferingDTO> courseOfferingDTOS = Arrays.asList(courseOfferingDTO);

        when(courseOfferingRepository.findAll()).thenReturn(courseOfferings);
        when(courseOfferingAdapter.getCourseOfferingDTOFromCourseOffering(any(CourseOffering.class))).thenReturn(courseOfferingDTO);

        Collection<CourseOfferingDTO> result = courseOfferingService.getAllCourseOfferings();

        assertNotNull(result);
        assertEquals(courseOfferingDTOS.size(), result.size());
    }

    @Test
    @WithMockUser
    void testUpdateCourseOffering_Success() {
        when(courseOfferingRepository.findById(1L)).thenReturn(Optional.of(courseOffering));
        when(courseService.getCourse(courseOfferingDTO.getCourseId())).thenReturn(Optional.of(courseDTO));
        when(courseAdapter.getCourseFromCourseDTO(courseDTO)).thenReturn(course);
        when(facultyService.getFaculty(courseOfferingDTO.getFacultyId())).thenReturn(faculty);
        when(sessionService.getSession(anyLong())).thenAnswer(invocation -> {
            Long sessionId = invocation.getArgument(0);
            return sessions.stream().filter(s -> s.getId()==sessionId).findFirst().orElse(null);
        });

        when(courseOfferingRepository.save(any(CourseOffering.class))).thenReturn(courseOffering);
        when(courseOfferingAdapter.getCourseOfferingDTOFromCourseOffering(any(CourseOffering.class))).thenReturn(courseOfferingDTO);

        CourseOfferingDTO result = courseOfferingService.updateCourseOffering(1L, courseOfferingDTO);

        assertNotNull(result);
        assertEquals(courseOfferingDTO.getCapacity(), result.getCapacity());
        assertEquals(courseOfferingDTO.getCredits(), result.getCredits());
        assertEquals(courseOfferingDTO.getRoom(), result.getRoom());
        assertEquals(courseOfferingDTO.getStartDate(), result.getStartDate());
        assertEquals(courseOfferingDTO.getEndDate(), result.getEndDate());
        assertEquals(courseOfferingDTO.getCourseOfferingType(), result.getCourseOfferingType());
        assertEquals(courseOfferingDTO.getCourseId(), result.getCourseId());
        assertEquals(courseOfferingDTO.getFacultyId(), result.getFacultyId());
        assertEquals(courseOfferingDTO.getSessionList().size(), result.getSessionList().size());

        verify(courseOfferingRepository, times(1)).save(any(CourseOffering.class));
        verify(courseService, times(1)).getCourse(courseOfferingDTO.getCourseId());
        verify(facultyService, times(1)).getFaculty(courseOfferingDTO.getFacultyId());
        verify(sessionService, times(3)).getSession(anyLong());
        verify(courseOfferingAdapter, times(1)).getCourseOfferingDTOFromCourseOffering(any(CourseOffering.class));
    }

    @Test
    void testDeleteCourseOffering() {
        doNothing().when(courseOfferingRepository).deleteById(1L);
        courseOfferingService.deleteCourseOffering(1L);
        verify(courseOfferingRepository, times(1)).deleteById(1L);
    }
}

