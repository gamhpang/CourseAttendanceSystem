package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.AttendanceRecord;
import cs544.courseattendancesystem.domain.Session;
import cs544.courseattendancesystem.repository.AttendanceRecordRepository;
import cs544.courseattendancesystem.service.adapter.AttendanceRecordAdapter;
import cs544.courseattendancesystem.service.adapter.SessionAdapter;
import cs544.courseattendancesystem.service.dto.AttendanceAndSessionFromOfferingDTO;
import cs544.courseattendancesystem.service.dto.AttendanceRecordDTO;
import cs544.courseattendancesystem.service.dto.SessionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class AttendanceAndSessionFromOfferingServiceTests {

    @TestConfiguration
    static class AttendanceAndSessionFromOfferingServiceImplTestContextConfiguration {

        @Bean
        public AttendanceAndSessionFromOfferingService attendanceAndSessionFromOfferingService() {
            return new AttendanceAndSessionFromOfferingServiceImpl();
        }
    }

    @Autowired
    private AttendanceAndSessionFromOfferingService attendanceAndSessionFromOfferingService;

    @MockBean
    private AttendanceRecordRepository attendanceRecordRepository;

    @MockBean
    private AttendanceRecordAdapter attendanceRecordAdapter;

    @MockBean
    private SessionService sessionService;

    private AttendanceRecord attendanceRecord;
    private AttendanceRecordDTO attendanceRecordDTO;
    private Session session;
    private SessionDTO sessionDTO;

    @BeforeEach
    public void setUp() {
        attendanceRecord = new AttendanceRecord();
        attendanceRecord.setId(1L);

        attendanceRecordDTO = new AttendanceRecordDTO();
        attendanceRecordDTO.setId(1L);

        session = new Session();
        session.setId(1L);

        sessionDTO = new SessionDTO();
        sessionDTO.setId(1L);

        Mockito.when(attendanceRecordRepository.getAttendanceRecordBySessionIdAndStudentId(1L, 1L))
                .thenReturn(Arrays.asList(attendanceRecord));

        Mockito.when(attendanceRecordAdapter.getAttendanceRecordDTOFromAttendanceRecord(attendanceRecord))
                .thenReturn(attendanceRecordDTO);

        Mockito.when(sessionService.getSession(1L)).thenReturn(session);
        //Mockito.when(SessionAdapter.getSessionDTOFromSession(session)).thenReturn(sessionDTO);
    }

    @Test
    public void whenValidSessionListAndStudentId_thenAttendanceAndSessionShouldBeFound() {
        List<Long> sessionList = Arrays.asList(1L);
        Long studentId = 1L;

        AttendanceAndSessionFromOfferingDTO resultDTO = attendanceAndSessionFromOfferingService.getAttendanceAndSession(sessionList, studentId);

        assertThat(resultDTO).isNotNull();
        assertThat(resultDTO.getAttendances()).hasSize(1);
        assertThat(resultDTO.getAttendances().get(0).getId()).isEqualTo(attendanceRecordDTO.getId());
        assertThat(resultDTO.getSessions()).hasSize(1);
        assertThat(resultDTO.getSessions().get(0).getId()).isEqualTo(sessionDTO.getId());
    }
}
