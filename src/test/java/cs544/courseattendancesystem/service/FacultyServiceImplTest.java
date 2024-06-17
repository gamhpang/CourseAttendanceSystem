package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Faculty;
import cs544.courseattendancesystem.repository.FacultyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class FacultyServiceImplTest {

    @Configuration
    static class FacultyServiceImplTestContextConfiguration {
        @Bean
        public FacultyServiceImpl facultyService() {
            return new FacultyServiceImpl();
        }
    }

    @Autowired
    private FacultyService facultyService;

    @MockBean
    private FacultyRepository facultyRepository;

    private Faculty faculty;

    @BeforeEach
    public void setUp() {
        faculty = new Faculty(LocalDate.of(1999, 10, 31), "john@miu.edu.tt", "John", "Doe", "john", "123", "Mr.", Arrays.asList("Listening", "Reading"));
        faculty.setId(1L);
    }

    @Test
    void testGetFaculty_Success() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        Faculty foundFaculty = facultyService.getFaculty(1L);

        assertNotNull(foundFaculty);
        assertEquals(faculty.getId(), foundFaculty.getId());
        assertEquals(faculty.getFirstName(), foundFaculty.getFirstName());
        verify(facultyRepository, times(1)).findById(1L);
    }

    @Test
    void testGetFaculty_NotFound() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());

        Faculty foundFaculty = facultyService.getFaculty(1L);

        assertNull(foundFaculty);
        verify(facultyRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateFaculty_Success() {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        Faculty createdFaculty = facultyService.createFaculty(
                LocalDate.of(1999, 10, 31),
                "john@miu.edu.tt",
                "John",
                "Doe",
                "john",
                "123",
                "Mr.",
                Arrays.asList("Listening", "Reading")
        );

        assertNotNull(createdFaculty);
        assertEquals(faculty.getFirstName(), createdFaculty.getFirstName());
        verify(facultyRepository, times(1)).save(any(Faculty.class));
    }
}
