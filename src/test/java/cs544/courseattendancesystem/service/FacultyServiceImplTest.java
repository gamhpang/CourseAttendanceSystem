package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Faculty;
import cs544.courseattendancesystem.repository.FacultyRepository;
import cs544.courseattendancesystem.service.adapter.FacultyAdapter;
import cs544.courseattendancesystem.service.dto.FacultyDTO;
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

import static cs544.courseattendancesystem.domain.GenderType.MALE;
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

    @MockBean
    private FacultyAdapter facultyAdapter;

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
    public void testCreateFaculty() {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setId(1L);
        facultyDTO.setFirstName("John");
        facultyDTO.setLastName("Doe");
        facultyDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        facultyDTO.setEmailAddress("john.doe@example.com");
        facultyDTO.setSalutation("Mr.");
        facultyDTO.setHobbies(Arrays.asList("Reading", "Gardening"));
        facultyDTO.setGenderType(MALE);

        // Mock the save method of facultyRepository
        when(facultyRepository.save(any(Faculty.class))).thenAnswer(invocation -> {
            Faculty faculty = invocation.getArgument(0);
            faculty.setId(1L); // Set ID manually or based on your mock logic
            return faculty;
        });

        // Call the method to test
        FacultyDTO createdFacultyDTO = facultyService.createFaculty(facultyDTO);

        // Verify the save method was called once
        verify(facultyRepository, times(1)).save(any(Faculty.class));

        // Assert that the returned DTO matches the expected DTO
        assertEquals(facultyDTO.getId(), createdFacultyDTO.getId());
        assertEquals(facultyDTO.getFirstName(), createdFacultyDTO.getFirstName());
        assertEquals(facultyDTO.getLastName(), createdFacultyDTO.getLastName());
        assertEquals(facultyDTO.getBirthDate(), createdFacultyDTO.getBirthDate());
        assertEquals(facultyDTO.getEmailAddress(), createdFacultyDTO.getEmailAddress());
        assertEquals(facultyDTO.getSalutation(), createdFacultyDTO.getSalutation());
        assertEquals(facultyDTO.getUserName(), createdFacultyDTO.getUserName());
        assertEquals(facultyDTO.getHobbies(), createdFacultyDTO.getHobbies());
        assertEquals(facultyDTO.getGenderType(), createdFacultyDTO.getGenderType());
    }

}
