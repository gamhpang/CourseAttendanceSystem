package cs544.courseattendancesystem.controller;

import cs544.courseattendancesystem.service.CourseService;
import cs544.courseattendancesystem.service.dto.CourseDTO;
import cs544.courseattendancesystem.service.dto.CustomerErrorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private CourseDTO courseDTO;

    @BeforeEach
    public void setUp() {
        courseDTO = new CourseDTO(1L, 4.0, "Description", "CS101", "Computer Science", "CS");
        courseDTO.getPrerequisites().add(2L);
    }

    @Test
    public void testGetAllCourses() throws Exception {
        Collection<CourseDTO> courseList = new ArrayList<>();
        courseList.add(courseDTO);

        Mockito.when(courseService.getAllCourses()).thenReturn(courseList);

        mockMvc.perform(get("/sys-admin/courses"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(courseDTO.getId()));
    }

    @Test
    public void testGetCourseById_NotFound() throws Exception {
        Mockito.when(courseService.getCourse(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/sys-admin/courses/1"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("Course with id= 1 is not available"));
    }

    @Test
    public void testGetCourseById_Found() throws Exception {
        Mockito.when(courseService.getCourse(1L)).thenReturn(Optional.of(courseDTO));

        mockMvc.perform(get("/sys-admin/courses/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(courseDTO.getId()));
    }

    @Test
    public void testAddCourse() throws Exception {
        Mockito.when(courseService.createCourse(Mockito.any(CourseDTO.class))).thenReturn(courseDTO);

        mockMvc.perform(post("/sys-admin/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"credits\":4.0,\"description\":\"Description\",\"code\":\"CS101\",\"name\":\"Computer Science\",\"department\":\"CS\",\"prerequisites\":[2]}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(courseDTO.getId()));
    }

    @Test
    public void testUpdateCourse() throws Exception {
        Mockito.when(courseService.updateCourse(Mockito.eq(1L), Mockito.any(CourseDTO.class))).thenReturn(courseDTO);

        mockMvc.perform(put("/sys-admin/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"credits\":4.0,\"description\":\"Description\",\"code\":\"CS101\",\"name\":\"Computer Science\",\"department\":\"CS\",\"prerequisites\":[2]}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(courseDTO.getId()));
    }

    @Test
    public void testDeleteCourse() throws Exception {
        Mockito.doNothing().when(courseService).deleteCourse(1L);

        mockMvc.perform(delete("/sys-admin/courses/1"))
                .andExpect(status().isNoContent());
    }
}
