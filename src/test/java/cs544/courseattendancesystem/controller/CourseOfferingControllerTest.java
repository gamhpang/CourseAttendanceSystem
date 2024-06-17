package cs544.courseattendancesystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs544.courseattendancesystem.domain.CourseOfferingType;
import cs544.courseattendancesystem.service.CourseOfferingService;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CourseOfferingControllerTest {

    // MockMvc is used to perform HTTP requests and assert responses
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseOfferingService courseOfferingService;

    @Test
    void testGetAllCourseOfferings() throws Exception{
        // Prepare test data
        List<CourseOfferingDTO> courseOfferingDTOS = Arrays.asList(
                new CourseOfferingDTO(1L, 3, "Room 101", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 12, 1), 30, CourseOfferingType.ON_CAMPUS, 1L, 1L, null),
                new CourseOfferingDTO(2L, 4, "Room 202", LocalDate.of(2023, 7, 1), LocalDate.of(2023, 12, 1), 40, CourseOfferingType.ON_CAMPUS, 2L, 2L, null)
        );
        //Mock the service method
        Mockito.when(courseOfferingService.getAllCourseOfferings())
                .thenReturn(courseOfferingDTOS);

        // Perform the GET request and check the response
        mockMvc.perform(get("/sys-admin/course-offerings"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].credits").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].room").value("Room 101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].startDate").value("2023-06-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endDate").value("2023-12-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].capacity").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseOfferingType").value("ON_CAMPUS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].facultyId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].credits").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].room").value("Room 202"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].startDate").value("2023-07-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endDate").value("2023-12-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].capacity").value(40))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseOfferingType").value("ON_CAMPUS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].facultyId").value(2L));
    }

    @Test
    void testGetCourseOfferingById() throws Exception{
        // Prepare test data
        CourseOfferingDTO courseOfferingDTO = new CourseOfferingDTO(1L, 3, "Room 101", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 12, 1), 30, CourseOfferingType.ON_CAMPUS, 1L, 1L, Arrays.asList(1L, 2L));
        Mockito.when(courseOfferingService.getCourseOffering(1L)).thenReturn(courseOfferingDTO);

        // Perform the GET request and check the response
        mockMvc.perform(get("/sys-admin/course-offerings/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.credits").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.room").value("Room 101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2023-06-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2023-12-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseOfferingType").value("ON_CAMPUS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.facultyId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sessionList[0]").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sessionList[1]").value(2L));
    }

    @Test
    void testGetCourseOfferingById_NotFound() throws Exception {
        // Mock the service method to return null
        Mockito.when(courseOfferingService.getCourseOffering(1L)).thenReturn(null);

        mockMvc.perform(get("/sys-admin/course-offerings/1"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("CourseOffering with id= 1 is not available"));
    }

    @Test
    void testUpdateCourseOffering() throws Exception {
        // Prepare the test data
        CourseOfferingDTO updatedCourseOfferingDTO = new CourseOfferingDTO(1L, 4, "Room 101 Updated", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 12, 1), 30, CourseOfferingType.ON_CAMPUS, 1L, 1L, Arrays.asList(1L, 2L));
        Mockito.when(courseOfferingService.updateCourseOffering(1L, updatedCourseOfferingDTO)).thenReturn(updatedCourseOfferingDTO);

        // Perform the update operation and verify the result
        mockMvc.perform(put("/sys-admin/course-offerings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedCourseOfferingDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.credits").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.room").value("Room 101 Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2023-06-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2023-12-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseOfferingType").value("ON_CAMPUS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.facultyId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sessionList[0]").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sessionList[1]").value(2L));

        // Verify that the service method was called once
        verify(courseOfferingService, times(1)).updateCourseOffering(1L, updatedCourseOfferingDTO);
    }

    @Test
    void testCreateCourseOffering() throws Exception {
        CourseOfferingDTO courseOfferingDTO = new CourseOfferingDTO(1L, 3, "Room 101", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 12, 1), 30, CourseOfferingType.ON_CAMPUS, 1L, 1L, Arrays.asList(1L, 2L));
        Mockito.when(courseOfferingService.createCourseOffering(courseOfferingDTO)).thenReturn(courseOfferingDTO);

        mockMvc.perform(post("/sys-admin/course-offerings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(courseOfferingDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.credits").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.room").value("Room 101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2023-06-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2023-12-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseOfferingType").value("ON_CAMPUS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.facultyId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sessionList[0]").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sessionList[1]").value(2L));
    }

    @Test
    void testDeleteCourseOffering() throws Exception {
        //Prepare test data
        CourseOfferingDTO courseOfferingDTO = new CourseOfferingDTO(1L, 3, "Room 101", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 12, 1), 30, CourseOfferingType.ON_CAMPUS, 1L, 1L, Arrays.asList(1L, 2L));

        Mockito.when(courseOfferingService.getCourseOffering(1L)).thenReturn(courseOfferingDTO);

        mockMvc.perform(delete("/sys-admin/course-offerings/1"))
                .andExpect(status().isOk());

        // Verify that the service method was called once
        verify(courseOfferingService, times(1)).deleteCourseOffering(1L);
    }

    public static String asJsonString(final Object obj){
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }


}
