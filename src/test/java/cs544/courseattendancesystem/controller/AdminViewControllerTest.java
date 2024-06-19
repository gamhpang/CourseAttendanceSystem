package cs544.courseattendancesystem.controller;

import cs544.courseattendancesystem.domain.CourseOfferingType;
import cs544.courseattendancesystem.service.CourseRegistrationService;
import cs544.courseattendancesystem.service.dto.CourseOfferingWithDetailsDTO;
import cs544.courseattendancesystem.service.dto.CourseWithGradeDTO;
import cs544.courseattendancesystem.service.dto.StudentDTO;
import cs544.courseattendancesystem.service.dto.StudentWithRegisterCourseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseRegistrationService courseRegistrationService;

    @Test
    void testGetAllCourseOfferings() throws Exception {
        // Prepare test data
        Collection<CourseOfferingWithDetailsDTO> courseOfferings = Arrays.asList(
                new CourseOfferingWithDetailsDTO(1L, 3.0, "Room 101", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 12, 1), 30, CourseOfferingType.ON_CAMPUS, 1L, "Course 1", "CSE101", "CS", 1L, "Faculty 1", null),
                new CourseOfferingWithDetailsDTO(2L, 4.0, "Room 202", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 12, 1), 40, CourseOfferingType.ON_CAMPUS, 2L, "Course 2", "CSE102", "CS", 2L, "Faculty 2", null)
        );

        // Mock the service method
        when(courseRegistrationService.getCourseOfferingDetails()).thenReturn(courseOfferings);

        // Perform the GET request and check the response
        mockMvc.perform(get("/admin-view/course-offerings"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseOfferingId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].credits").value(3.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].room").value("Room 101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].startDate").value("2023-06-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endDate").value("2023-12-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].capacity").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseOfferingType").value("ON_CAMPUS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseName").value("Course 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseCode").value("CSE101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseDepartment").value("CS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].facultyId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].facultyName").value("Faculty 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseOfferingId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].credits").value(4.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].room").value("Room 202"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].startDate").value("2023-07-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endDate").value("2023-12-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].capacity").value(40))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseOfferingType").value("ON_CAMPUS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseName").value("Course 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseCode").value("CSE102"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseDepartment").value("CS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].facultyId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].facultyName").value("Faculty 2"));
    }

    @Test
    void testGetCourseOfferingById() throws Exception {
        // Prepare test data
        CourseOfferingWithDetailsDTO courseOffering = new CourseOfferingWithDetailsDTO(1L, 3.0, "Room 101", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 12, 1), 30, CourseOfferingType.ON_CAMPUS, 1L, "Course 1", "CSE101", "CS", 1L, "Faculty 1", null);
        when(courseRegistrationService.getCourseOfferingDetailsWithId(1L)).thenReturn(courseOffering);

        // Perform the GET request and check the response
        mockMvc.perform(get("/admin-view/course-offerings/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseOfferingId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.credits").value(3.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.room").value("Room 101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2023-06-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2023-12-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseOfferingType").value("ON_CAMPUS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseName").value("Course 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseCode").value("CSE101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseDepartment").value("CS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.facultyId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.facultyName").value("Faculty 1"));
    }

    @Test
    void testGetCourseOfferingById_NotFound() throws Exception {
        // Mock the service method to return null
        when(courseRegistrationService.getCourseOfferingDetailsWithId(1L)).thenReturn(null);

        mockMvc.perform(get("/admin-view/course-offerings/1"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("CourseOffering with id= 1 is not available"));
    }

    @Test
    void testGetCourseOfferingByStudent() throws Exception {
        CourseWithGradeDTO courseWithGradeDTO = new CourseWithGradeDTO();
        courseWithGradeDTO.setCourseName("Enterprise Architecture");
        courseWithGradeDTO.setGrade("A");

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(1L);
        studentDTO.setFirstName("John");
        studentDTO.setLastName("Doe");

        StudentWithRegisterCourseDTO studentWithRegisterCourseDTO = new StudentWithRegisterCourseDTO();
        studentWithRegisterCourseDTO.setStudent(studentDTO);
        studentWithRegisterCourseDTO.setCourseWithGradeDTOCollection(Arrays.asList(courseWithGradeDTO));

        when(courseRegistrationService.getCourseOfferingByStudent(1L)).thenReturn(studentWithRegisterCourseDTO);

        mockMvc.perform(get("/admin-view/students/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.student.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.student.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.student.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseWithGradeDTOCollection[0].courseName").value("Enterprise Architecture"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseWithGradeDTOCollection[0].grade").value("A"));
    }

    @Test
    void testGetCourseOfferingByStudent_NotFound() throws Exception {
        when(courseRegistrationService.getCourseOfferingByStudent(1L)).thenReturn(null);

        mockMvc.perform(get("/admin-view/students/1"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("CourseOffering with Student id= 1 is not available"));
    }
}
