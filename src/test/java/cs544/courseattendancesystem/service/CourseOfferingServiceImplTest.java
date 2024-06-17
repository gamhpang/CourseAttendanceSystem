package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Course;
import cs544.courseattendancesystem.domain.CourseOffering;
import cs544.courseattendancesystem.domain.CourseOfferingType;
import cs544.courseattendancesystem.domain.Faculty;
import cs544.courseattendancesystem.domain.Session;
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


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CourseOfferingServiceImplTest {

    @TestConfiguration
    static class CourseOfferingServiceImplTestContextConfiguration{
        @Bean
        public CourseOfferingServiceImpl courseOfferingService(){
            return new CourseOfferingServiceImpl();
        }
    }

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

    private CourseOfferingDTO courseOfferingDTO;
    private CourseOffering courseOffering;
    private CourseDTO courseDTO;
    private Course course;
    private Faculty faculty;
    private List<Session> sessions;

    @BeforeEach
    public void setUp() {
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
    public void testCreateCourseOffering_Success() {
        when(courseService.getCourse(courseOfferingDTO.getCourseId())).thenReturn(Optional.of(courseDTO));
        when(courseAdapter.getCourseFromCourseDTO(courseDTO)).thenReturn(course);
        when(facultyService.getFaculty(courseOfferingDTO.getFacultyId())).thenReturn(faculty);
        when(sessionService.generateSessions(courseOfferingDTO.getStartDate(), courseOfferingDTO.getEndDate())).thenReturn(sessions);
        when(courseOfferingRepository.save(any(CourseOffering.class))).thenReturn(courseOffering);
        when(courseOfferingAdapter.getCourseOfferingDTOFromCourseOffering(courseOffering)).thenReturn(courseOfferingDTO);

        CourseOfferingDTO result = courseOfferingService.createCourseOffering(courseOfferingDTO);

        assertNotNull(result);
        assertEquals(courseOfferingDTO, result);
        verify(courseOfferingRepository, times(1)).save(any(CourseOffering.class));
    }

    @Test
    public void testCreateCourseOffering_CourseNotFound() {
        when(courseService.getCourse(courseOfferingDTO.getCourseId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            courseOfferingService.createCourseOffering(courseOfferingDTO);
        });

        assertEquals("Course not found with Id: 1", exception.getMessage());
    }

    @Test
    public void testGetCourseOffering_Success() {
        when(courseOfferingRepository.findById(1L)).thenReturn(Optional.of(courseOffering));
        when(courseOfferingAdapter.getCourseOfferingDTOFromCourseOffering(courseOffering)).thenReturn(courseOfferingDTO);

        CourseOfferingDTO result = courseOfferingService.getCourseOffering(1L);

        assertNotNull(result);
        assertEquals(courseOfferingDTO, result);
    }

    @Test
    public void testGetCourseOffering_NotFound() {
        when(courseOfferingRepository.findById(1L)).thenReturn(Optional.empty());

        CourseOfferingDTO result = courseOfferingService.getCourseOffering(1L);

        assertNull(result);
    }

    @Test
    public void testGetAllCourseOfferings() {
        List<CourseOffering> courseOfferings = Arrays.asList(courseOffering);
        List<CourseOfferingDTO> courseOfferingDTOS = Arrays.asList(courseOfferingDTO);

        when(courseOfferingRepository.findAll()).thenReturn(courseOfferings);
        when(courseOfferingAdapter.getCourseOfferingDTOFromCourseOffering(any(CourseOffering.class))).thenReturn(courseOfferingDTO);

        Collection<CourseOfferingDTO> result = courseOfferingService.getAllCourseOfferings();

        assertNotNull(result);
        assertEquals(courseOfferingDTOS.size(), result.size());
    }

    @Test
    public void testUpdateCourseOffering_Success() {
        when(courseOfferingRepository.findById(1L)).thenReturn(Optional.of(courseOffering));
        when(courseService.getCourse(courseOfferingDTO.getCourseId())).thenReturn(Optional.of(courseDTO));
        when(courseAdapter.getCourseFromCourseDTO(courseDTO)).thenReturn(course);
        when(facultyService.getFaculty(courseOfferingDTO.getFacultyId())).thenReturn(faculty);
        when(sessionService.generateSessions(courseOfferingDTO.getStartDate(), courseOfferingDTO.getEndDate())).thenReturn(sessions);
        when(courseOfferingRepository.save(courseOffering)).thenReturn(courseOffering);
        when(courseOfferingAdapter.getCourseOfferingDTOFromCourseOffering(courseOffering)).thenReturn(courseOfferingDTO);

        courseOfferingDTO.setSessionList(Arrays.asList(1L, 2L, 3L));

        CourseOfferingDTO result = courseOfferingService.updateCourseOffering(1L, courseOfferingDTO);

        assertNotNull(result);
        assertEquals(courseOfferingDTO, result);
        verify(courseOfferingRepository, times(1)).save(courseOffering);
    }

    @Test
    public void testUpdateCourseOffering_NotFound() {
        when(courseOfferingRepository.findById(1L)).thenReturn(Optional.empty());

        CourseOfferingDTO result = courseOfferingService.updateCourseOffering(1L, courseOfferingDTO);

        assertNull(result);
    }

    @Test
    public void testDeleteCourseOffering() {
        doNothing().when(courseOfferingRepository).deleteById(1L);

        courseOfferingService.deleteCourseOffering(1L);

        verify(courseOfferingRepository, times(1)).deleteById(1L);
    }
}
