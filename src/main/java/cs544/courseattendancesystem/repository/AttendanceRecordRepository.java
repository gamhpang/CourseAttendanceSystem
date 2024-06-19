package cs544.courseattendancesystem.repository;

import cs544.courseattendancesystem.domain.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import java.util.Optional;
@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord,Long> {
    @Query("Select att from AttendanceRecord att where att.session.id =:sessionId and att.student.id =:studentId")
    List<AttendanceRecord> getAttendanceRecordBySessionIdAndStudentId(@Param("studentId") long studentId, @Param("sessionId") long sessionId);

    @Query("Select att from AttendanceRecord att where att.session.id =:sessionId")
    List<AttendanceRecord> getAttendanceRecordBySessionId(@Param("sessionId") long sessionId);

    Collection<AttendanceRecord> findByStudentId(Long studentId);
}
