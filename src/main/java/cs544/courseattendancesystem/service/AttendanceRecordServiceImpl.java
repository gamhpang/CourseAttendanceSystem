package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.AttendanceRecord;
import cs544.courseattendancesystem.domain.Student;
import cs544.courseattendancesystem.repository.AttendanceRecordRepository;
import cs544.courseattendancesystem.service.adapter.AttendanceRecordAdapter;
import cs544.courseattendancesystem.service.dto.AttendanceRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class AttendanceRecordServiceImpl implements AttendanceRecordService{
    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;
    @Autowired
    private AttendanceRecordAdapter attendanceRecordAdapter;
    @Autowired
    private StudentService studentService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private SessionService sessionService;
    @Override
    public AttendanceRecordDTO createAttendance(AttendanceRecordDTO attendanceRecordDTO) {
        AttendanceRecord record = new AttendanceRecord();
        record.setScanDateTime(attendanceRecordDTO.getScanDateTime());
        record.setStudent(studentService.getStudentById(attendanceRecordDTO.getStudentId()));
        record.setLocation(locationService.getLocationById(attendanceRecordDTO.getLocationId()));
        record.setSession(sessionService.getSession(attendanceRecordDTO.getSessionId()));
        attendanceRecordRepository.save(record);
        AttendanceRecordDTO dto = attendanceRecordAdapter.getAttendanceRecordDTOFromAttendanceRecord(record);
        return dto;
    }

    @Override
    public Optional<AttendanceRecord> getAttendanceRecord(long recordId) {
        Optional<AttendanceRecord> attendanceRecord = attendanceRecordRepository.findById(recordId);
        return attendanceRecord;
    }
    @Override
    public AttendanceRecordDTO getAttendanceRecordDTO(long recordId) {
        Optional<AttendanceRecord> attendanceRecord = attendanceRecordRepository.findById(recordId);
        if(attendanceRecord.isPresent()){
            return attendanceRecordAdapter.getAttendanceRecordDTOFromAttendanceRecord(attendanceRecord.get());
        }
        return null;
    }

    @Override
    public List<AttendanceRecordDTO> getAttendanceRecordDTOBySessionId(long sessionId){
        List<AttendanceRecord> attendanceRecord = attendanceRecordRepository.getAttendanceRecordBySessionId(sessionId);
        return attendanceRecord.stream().map(attendanceRecordAdapter::getAttendanceRecordDTOFromAttendanceRecord).toList();
    }

    @Override
    public List<AttendanceRecordDTO> getAllAttendanceRecordDTO(){
        return attendanceRecordRepository.findAll().stream().map(attendanceRecordAdapter::getAttendanceRecordDTOFromAttendanceRecord).toList();
    }

    @Override
    public Collection<AttendanceRecordDTO> getAttendanceRecordByStudentId(long studentId) {
        System.out.println("At the service........." + studentId);

        return attendanceRecordAdapter.getAllAttendanceRecord(attendanceRecordRepository.findByStudentId(studentId));
    }
}
