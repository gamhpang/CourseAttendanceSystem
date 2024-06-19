package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.AttendanceRecord;
import cs544.courseattendancesystem.service.dto.AttendanceRecordDTO;
import cs544.courseattendancesystem.service.dto.AttendanceRecordFullDataDTO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AttendanceRecordService {
     AttendanceRecordDTO createAttendance(AttendanceRecordDTO attendanceRecord);
     Optional<AttendanceRecord> getAttendanceRecord(long recordId);
     AttendanceRecordDTO getAttendanceRecordDTO(long recordId);
     List<AttendanceRecordDTO> getAttendanceRecordDTOBySessionId(long sessionId);
     List<AttendanceRecordDTO> getAllAttendanceRecordDTO();
     Collection<AttendanceRecordFullDataDTO> getAttendanceRecordByStudentId(long studentId);
}
