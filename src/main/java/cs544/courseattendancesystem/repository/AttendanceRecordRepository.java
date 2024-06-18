package cs544.courseattendancesystem.repository;

import cs544.courseattendancesystem.domain.AttendanceRecord;
import cs544.courseattendancesystem.service.dto.AttendanceRecordDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord,Long> {
    Collection<AttendanceRecord> findByStudentId(Long studentId);
}
