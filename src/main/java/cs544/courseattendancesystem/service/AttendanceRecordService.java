package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.AttendanceRecord;
import cs544.courseattendancesystem.service.dto.AttendanceRecordDTO;
import cs544.courseattendancesystem.service.dto.AttendanceRecordFullDataDTO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AttendanceRecordService {
    public AttendanceRecordDTO createAttendance(AttendanceRecordDTO attendanceRecord);
    public Optional<AttendanceRecord> getAttendanceRecord(long recordId);
    public AttendanceRecordDTO getAttendanceRecordDTO(long recordId);
    public List<AttendanceRecordDTO> getAttendanceRecordDTOBySessionId(long sessionId);
    public List<AttendanceRecordDTO> getAllAttendanceRecordDTO();
    public Collection<AttendanceRecordFullDataDTO> getAttendanceRecordByStudentId(long studentId);
}
