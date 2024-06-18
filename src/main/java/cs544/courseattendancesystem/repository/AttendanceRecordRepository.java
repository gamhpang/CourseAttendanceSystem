package cs544.courseattendancesystem.repository;

import cs544.courseattendancesystem.domain.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord,Long> {
}
