package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.AttendanceRecord;

import java.util.Optional;

public interface AttendanceRecordService {
    public AttendanceRecord createAttendance(AttendanceRecord attendanceRecord);
    public Optional<AttendanceRecord> getAttendanceRecord(long recordId);
}
