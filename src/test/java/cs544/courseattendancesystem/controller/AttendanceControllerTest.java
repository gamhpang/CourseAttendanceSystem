package cs544.courseattendancesystem.controller;

import cs544.courseattendancesystem.controller.AttendanceController;
import cs544.courseattendancesystem.service.AttendanceRecordService;
import cs544.courseattendancesystem.service.dto.AttendanceRecordDTO;
import cs544.courseattendancesystem.service.dto.CustomerErrorType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AttendanceController.class)
public class AttendanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttendanceRecordService attendanceRecordService;

    @Test
    public void testCreateAttendanceRecord() throws Exception {
        AttendanceRecordDTO attendanceRecordDTO = new AttendanceRecordDTO();
        // populate attendanceRecordDTO with necessary data

        Mockito.when(attendanceRecordService.createAttendance(Mockito.any(AttendanceRecordDTO.class)))
                .thenReturn(attendanceRecordDTO);

        ResultActions result = mockMvc.perform(post("/sys-admin/attendance-record")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"studentId\": 1, \"sessionId\": 1, \"locationId\": 1, \"scanDateTime\": \"2023-06-15T10:00:00\"}"));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentId").value(attendanceRecordDTO.getStudentId()))
                .andExpect(jsonPath("$.sessionId").value(attendanceRecordDTO.getSessionId()))
                .andExpect(jsonPath("$.locationId").value(attendanceRecordDTO.getLocationId()));
    }

    @Test
    public void testGetAttendanceRecord() throws Exception {
        AttendanceRecordDTO attendanceRecordDTO = new AttendanceRecordDTO();
        // populate attendanceRecordDTO with necessary data
        attendanceRecordDTO.setId(1L);

        Mockito.when(attendanceRecordService.getAttendanceRecordDTO(1L))
                .thenReturn(attendanceRecordDTO);

        mockMvc.perform(get("/sys-admin/attendance-record/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testGetAttendanceRecordNotFound() throws Exception {
        Mockito.when(attendanceRecordService.getAttendanceRecordDTO(1L))
                .thenReturn(null);

        mockMvc.perform(get("/sys-admin/attendance-record/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Attendance record with id= 1 is not available"));
    }

    @Test
    public void testGetAllAttendanceRecords() throws Exception {
        AttendanceRecordDTO attendanceRecordDTO = new AttendanceRecordDTO();
        // populate attendanceRecordDTO with necessary data
        List<AttendanceRecordDTO> attendanceRecordList = Arrays.asList(attendanceRecordDTO);

        Mockito.when(attendanceRecordService.getAllAttendanceRecordDTO())
                .thenReturn(attendanceRecordList);

        mockMvc.perform(get("/sys-admin/attendance-record"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].studentId").value(attendanceRecordDTO.getStudentId()));
    }

    @Test
    public void testGetAllAttendanceRecordsEmpty() throws Exception {
        Mockito.when(attendanceRecordService.getAllAttendanceRecordDTO())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/sys-admin/attendance-record"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Attendance record is empty"));
    }
}
