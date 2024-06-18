package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.AttendanceRecord;
import cs544.courseattendancesystem.service.dto.AttendanceRecordDTO;

import java.util.Collection;
import java.util.Optional;
import java.util.OptionalInt;

public interface AttendanceRecordService {
    public AttendanceRecord createAttendance(AttendanceRecord attendanceRecord);
    public Optional<AttendanceRecord> getAttendanceRecord(long recordId);

    public Collection<AttendanceRecordDTO> getAttendanceRecordByStudentId(long studentId);
}
