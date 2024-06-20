package cs544.courseattendancesystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs544.courseattendancesystem.domain.CourseOfferingType;
import cs544.courseattendancesystem.service.*;
import cs544.courseattendancesystem.service.dto.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseOfferingService courseOfferingService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private CourseRegistrationService courseRegistrationService;

    @MockBean
    private AttendanceAndSessionFromOfferingService attendanceAndSessionFromOfferingService;

    @MockBean
    private AttendanceRecordService attendanceRecordService;

    @Test
    @WithMockUser
    void testGetCourseWithGrade() throws Exception {
        // Prepare test data
        List<CourseWithGradeDTO> courseWithGrades = Arrays.asList(
                new CourseWithGradeDTO(1L, "Course 1", "CSE101", "Department 1", 1L, "Faculty 1", "A", 30, 1L, 3.0, "Room 101", CourseOfferingType.ON_CAMPUS, LocalDate.of(2023, 6, 1), LocalDate.of(2023, 12, 1)),
                new CourseWithGradeDTO(2L, "Course 2", "CSE102", "Department 2", 2L, "Faculty 2", "B", 40, 2L, 4.0, "Room 102", CourseOfferingType.ON_CAMPUS, LocalDate.of(2023, 7, 1), LocalDate.of(2023, 12, 1))
        );

        // Mock the service method
        Mockito.when(courseRegistrationService.getCourseOfferingWithGradeDTO(1L)).thenReturn(courseWithGrades);

        // Perform the GET request and check the response
        mockMvc.perform(get("/student-view/course-offerings")
                        .header("studentId", 1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseName").value("Course 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseCode").value("CSE101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseDepartment").value("Department 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].facultyId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].facultyName").value("Faculty 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].grade").value("A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].capacity").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseOfferingId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].credits").value(3.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].room").value("Room 101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseOfferingType").value("ON_CAMPUS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].startDate").value("2023-06-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endDate").value("2023-12-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseName").value("Course 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseCode").value("CSE102"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseDepartment").value("Department 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].facultyId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].facultyName").value("Faculty 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].grade").value("B"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].capacity").value(40))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseOfferingId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].credits").value(4.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].room").value("Room 102"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseOfferingType").value("ON_CAMPUS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].startDate").value("2023-07-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endDate").value("2023-12-01"));
    }

    @Test
    @WithMockUser
    void testCreateCourseRegistration() throws Exception {
        // Prepare test data
        CourseRegistrationDTO courseRegistrationDTO = new CourseRegistrationDTO();
        courseRegistrationDTO.setStudentId(1L);
        courseRegistrationDTO.setCourseOfferingId(1L);
        courseRegistrationDTO.setGrade("A");

        // Perform the POST request
        mockMvc.perform(post("/student-view/course-registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(courseRegistrationDTO)))
                .andExpect(status().isCreated());

        // Verify that the service method was called once
        verify(courseRegistrationService, times(1)).createCourseRegistration(courseRegistrationDTO);
    }

    @Test
    @WithMockUser
    void testGetCourseOfferingByOfferingId() throws Exception {
        // Prepare test data
        long offeringId = 1L;
        CourseOfferingWithDetailsDTO courseOfferingWithDetailsDTO = new CourseOfferingWithDetailsDTO();

        // Mock the service method
        Mockito.when(courseRegistrationService.getCourseOfferingDetailsWithId(offeringId)).thenReturn(courseOfferingWithDetailsDTO);

        // Perform the GET request and check the response
        mockMvc.perform(get("/student-view/course-offerings/{offeringId}", offeringId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseOfferingId").exists());
    }

    @Test
    @WithMockUser
    void testGetAttendanceFromCourseOfferings_CourseOfferingNotFound() throws Exception {
        long studentId = 1L;
        long offeringId = 1L;
        StudentDTO studentDTO = new StudentDTO();

        Mockito.when(studentService.getStudent(studentId)).thenReturn(studentDTO);
        Mockito.when(courseOfferingService.getCourseOffering(offeringId)).thenReturn(null);

        mockMvc.perform(get("/student-view/course-offerings/{offeringId}/attendance", offeringId)
                        .header("studentId", studentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testGetAttendanceFromCourseOfferings_StudentNotFound() throws Exception {
        long studentId = 1L;
        long offeringId = 1L;

        Mockito.when(studentService.getStudent(studentId)).thenReturn(null);

        mockMvc.perform(get("/student-view/course-offerings/{offeringId}/attendance", offeringId)
                        .header("studentId", studentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testGetAllAttendanceRecord() throws Exception {
        // Prepare test data
        long studentId = 1L;
        Collection<AttendanceRecordFullDataDTO> attendanceRecords = Arrays.asList(new AttendanceRecordFullDataDTO(), new AttendanceRecordFullDataDTO());

        // Mock the service method
        Mockito.when(attendanceRecordService.getAttendanceRecordByStudentId(studentId)).thenReturn(attendanceRecords);

        // Perform the GET request and check the response
        mockMvc.perform(get("/student-view/attendance-records")
                        .header("studentId", studentId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(attendanceRecords.size()));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
