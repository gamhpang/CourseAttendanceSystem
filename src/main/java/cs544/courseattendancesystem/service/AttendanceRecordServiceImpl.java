package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.AttendanceRecord;
import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.repository.AttendanceRecordRepository;
import cs544.courseattendancesystem.service.adapter.AttendanceRecordAdapter;
import cs544.courseattendancesystem.service.dto.AttendanceRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
@Service
public class AttendanceRecordServiceImpl implements AttendanceRecordService{
    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Autowired
    private AttendanceRecordAdapter attendanceRecordAdapter;
    @Override
    public AttendanceRecord createAttendance(AttendanceRecord attendanceRecord) {
        return null;
    }

    @Override
    public Optional<AttendanceRecord> getAttendanceRecord(long recordId) {
        return Optional.empty();
    }

    @Override
    public Collection<AttendanceRecordDTO> getAttendanceRecordByStudentId(long studentId) {
        System.out.println("At the service........." + studentId);

        return attendanceRecordAdapter.getAllAttendanceRecord(attendanceRecordRepository.findByStudentId(studentId));
    }
}
