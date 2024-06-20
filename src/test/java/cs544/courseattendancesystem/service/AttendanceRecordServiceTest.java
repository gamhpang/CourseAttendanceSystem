package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.AttendanceRecord;
import cs544.courseattendancesystem.domain.Student;
import cs544.courseattendancesystem.repository.AttendanceRecordRepository;
import cs544.courseattendancesystem.service.adapter.AttendanceRecordAdapter;
import cs544.courseattendancesystem.service.dto.AttendanceRecordDTO;
import cs544.courseattendancesystem.service.dto.AttendanceRecordFullDataDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class AttendanceRecordServiceTest {

    @TestConfiguration
    static class AttendanceRecordServiceImplTestContextConfiguration {

        @Bean
        public AttendanceRecordService attendanceRecordService() {
            return new AttendanceRecordServiceImpl();
        }
    }

    @Autowired
    private AttendanceRecordService attendanceRecordService;

    @MockBean
    private AttendanceRecordRepository attendanceRecordRepository;

    @MockBean
    private AttendanceRecordAdapter attendanceRecordAdapter;

    @MockBean
    private StudentService studentService;

    @MockBean
    private LocationService locationService;

    @MockBean
    private SessionService sessionService;

    private AttendanceRecord attendanceRecord;
    private AttendanceRecordDTO attendanceRecordDTO;

    private AttendanceRecordFullDataDTO attendanceRecordFullDataDTO;
    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setId(1L);

        attendanceRecord = new AttendanceRecord();
        attendanceRecord.setId(1L);
        attendanceRecord.setScanDateTime(LocalDateTime.now());
        attendanceRecord.setStudent(student);

        attendanceRecordDTO = new AttendanceRecordDTO();
        attendanceRecordDTO.setId(1L);
        attendanceRecordDTO.setScanDateTime(LocalDateTime.now());
        attendanceRecordDTO.setStudentId(1L);

        attendanceRecordFullDataDTO = new AttendanceRecordFullDataDTO();
        attendanceRecordFullDataDTO.setId(1L);
        attendanceRecordFullDataDTO.setScanDateTime(LocalDateTime.now());
//        attendanceRecordFullDataDTO.setStudentId(1L);

        Mockito.when(attendanceRecordRepository.findById(attendanceRecord.getId()))
                .thenReturn(Optional.of(attendanceRecord));

        Mockito.when(attendanceRecordRepository.save(any(AttendanceRecord.class)))
                .thenReturn(attendanceRecord);

        Mockito.when(attendanceRecordAdapter.getAttendanceRecordDTOFromAttendanceRecord(any(AttendanceRecord.class)))
                .thenReturn(attendanceRecordDTO);

        Mockito.when(studentService.getStudentById(1L)).thenReturn(student);
    }

    @Test
    public void whenValidId_thenAttendanceRecordShouldBeFound() {
        Long id = 1L;
        Optional<AttendanceRecord> found = attendanceRecordService.getAttendanceRecord(id);

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(id);
    }

    @Test
    public void whenCreateAttendanceRecord_thenAttendanceRecordShouldBeCreated() {
        AttendanceRecordDTO dto = attendanceRecordService.createAttendance(attendanceRecordDTO);

        assertThat(dto.getId()).isEqualTo(attendanceRecordDTO.getId());
        assertThat(dto.getStudentId()).isEqualTo(attendanceRecordDTO.getStudentId());
    }

    @Test
    public void whenGetAttendanceRecordDTOByValidId_thenAttendanceRecordDTOShouldBeFound() {
        Long id = 1L;
        AttendanceRecordDTO dto = attendanceRecordService.getAttendanceRecordDTO(id);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(id);
    }

    @Test
    public void whenGetAllAttendanceRecordDTO_thenAllAttendanceRecordDTOsShouldBeReturned() {
        List<AttendanceRecord> records = Arrays.asList(attendanceRecord);
        List<AttendanceRecordDTO> dtos = Arrays.asList(attendanceRecordDTO);

        Mockito.when(attendanceRecordRepository.findAll()).thenReturn(records);
        Mockito.when(attendanceRecordAdapter.getAttendanceRecordDTOFromAttendanceRecord(attendanceRecord)).thenReturn(attendanceRecordDTO);

        List<AttendanceRecordDTO> found = attendanceRecordService.getAllAttendanceRecordDTO();

        assertThat(found).isNotEmpty();
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0).getId()).isEqualTo(attendanceRecordDTO.getId());
    }

    @Test
    public void whenGetAttendanceRecordByStudentId_thenAttendanceRecordDTOsShouldBeReturned() {
        List<AttendanceRecord> records = Arrays.asList(attendanceRecord);
        List<AttendanceRecordFullDataDTO> dtos = Arrays.asList(attendanceRecordFullDataDTO);

        Mockito.when(attendanceRecordRepository.findByStudentId(1L)).thenReturn(records);
        Mockito.when(attendanceRecordAdapter.getAllAttendanceRecord(records)).thenReturn(dtos);

        Collection<AttendanceRecordFullDataDTO> found = attendanceRecordService.getAttendanceRecordByStudentId(1L);

        assertThat(found).isNotEmpty();
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.iterator().next().getId()).isEqualTo(attendanceRecordDTO.getId());
    }
}
