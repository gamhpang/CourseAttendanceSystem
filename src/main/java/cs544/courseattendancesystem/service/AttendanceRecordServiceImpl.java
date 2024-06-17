package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.AttendanceRecord;
import cs544.courseattendancesystem.repository.AttendanceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AttendanceRecordServiceImpl implements AttendanceRecordService{
    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;
    @Override
    public AttendanceRecord createAttendance(AttendanceRecord attendanceRecord) {
        return null;
    }

    @Override
    public Optional<AttendanceRecord> getAttendanceRecord(long recordId) {
        Optional<AttendanceRecord> attendanceRecord = attendanceRecordRepository.findById(recordId);
        return attendanceRecord;
    }
}
