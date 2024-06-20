package cs544.courseattendancesystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs544.courseattendancesystem.service.StudentService;
import cs544.courseattendancesystem.service.dto.StudentDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void testCreateStudent_Success() throws Exception {
        // Prepare test data
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(1L);
        studentDTO.setFirstName("John Doe");
        studentDTO.setEmailAddress("johndoe@example.com");

        StudentDTO createdStudentDTO = new StudentDTO();
        createdStudentDTO.setId(1L);
        createdStudentDTO.setFirstName("John Doe");
        createdStudentDTO.setEmailAddress("johndoe@example.com");

        // Mock the service method
        Mockito.when(studentService.createStudentByDTO(studentDTO)).thenReturn(createdStudentDTO);

        // Perform the POST request and check the response
        mockMvc.perform(post("/sys-admin/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(studentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John Doe"));
    }

    @Test
    public void testGetStudent() throws Exception {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName("John");
        studentDTO.setLastName("Doe");

        when(studentService.getStudent(anyLong())).thenReturn(studentDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/sys-admin/students/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void testGetAllStudents() throws Exception {
        StudentDTO student1 = new StudentDTO();
        student1.setFirstName("John");
        student1.setLastName("Doe");

        StudentDTO student2 = new StudentDTO();
        student2.setFirstName("Jane");
        student2.setLastName("Smith");

        Collection<StudentDTO> studentList = Arrays.asList(student1, student2);

        when(studentService.getAllStudents()).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/sys-admin/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName("John");
        studentDTO.setLastName("Doe");

        when(studentService.updateStudent(anyLong(), Mockito.any(StudentDTO.class))).thenReturn(studentDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/sys-admin/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"facultyId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        doNothing().when(studentService).deleteStudent(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/sys-admin/students/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(studentService, Mockito.times(1)).deleteStudent(1L);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}