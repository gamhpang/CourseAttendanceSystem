package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Course;
import cs544.courseattendancesystem.repository.CourseRepository;
import cs544.courseattendancesystem.service.adapter.CourseAdapter;
import cs544.courseattendancesystem.service.dto.CourseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseAdapter courseAdapter;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course;
    private CourseDTO courseDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        course = new Course(4.0, "Description", "CS101", "Computer Science", "CS");
        course.setId(1L);

        courseDTO = new CourseDTO(1L, 4.0, "Description", "CS101", "Computer Science", "CS");
        courseDTO.getPrerequisites().add(2L);
    }

    @Test
    public void testCreateCourse() {
        Mockito.when(courseRepository.save(any(Course.class))).thenReturn(course);
        Mockito.when(courseAdapter.getCourseDTOFromCourse(any(Course.class))).thenReturn(courseDTO);
        Mockito.when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        CourseDTO createdCourse = courseService.createCourse(courseDTO);

        assertNotNull(createdCourse);
        assertEquals(courseDTO.getId(), createdCourse.getId());
    }

    @Test
    public void testGetCourse_Found() {
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Mockito.when(courseAdapter.getCourseDTOFromCourse(any(Course.class))).thenReturn(courseDTO);

        Optional<CourseDTO> foundCourse = courseService.getCourse(1L);

        assertTrue(foundCourse.isPresent());
        assertEquals(courseDTO.getId(), foundCourse.get().getId());
    }

    @Test
    public void testGetCourse_NotFound() {
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<CourseDTO> foundCourse = courseService.getCourse(1L);

        assertFalse(foundCourse.isPresent());
    }

    @Test
    public void testGetAllCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(course);

        List<CourseDTO> courseDTOList = new ArrayList<>();
        courseDTOList.add(courseDTO);

        Mockito.when(courseRepository.findAll()).thenReturn(courseList);
        Mockito.when(courseAdapter.getCourseDTOFromCourse(any(Course.class))).thenReturn(courseDTO);

        Collection<CourseDTO> allCourses = courseService.getAllCourses();

        assertNotNull(allCourses);
        assertEquals(1, allCourses.size());
    }

    @Test
    public void testUpdateCourse_Found() {
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Mockito.when(courseRepository.save(any(Course.class))).thenReturn(course);
        Mockito.when(courseAdapter.getCourseDTOFromCourse(any(Course.class))).thenReturn(courseDTO);
        Mockito.when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        CourseDTO updatedCourse = courseService.updateCourse(1L, courseDTO);

        assertNotNull(updatedCourse);
        assertEquals(courseDTO.getId(), updatedCourse.getId());
    }

    @Test
    public void testUpdateCourse_NotFound() {
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        CourseDTO updatedCourse = courseService.updateCourse(1L, courseDTO);

        assertNull(updatedCourse);
    }

    @Test
    public void testDeleteCourse() {
        Mockito.doNothing().when(courseRepository).deleteById(1L);

        courseService.deleteCourse(1L);

        Mockito.verify(courseRepository, Mockito.times(1)).deleteById(1L);
    }
}
