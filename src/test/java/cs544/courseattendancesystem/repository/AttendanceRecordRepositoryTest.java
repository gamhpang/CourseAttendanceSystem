package cs544.courseattendancesystem.repository;
import cs544.courseattendancesystem.domain.*;
import cs544.courseattendancesystem.repository.AttendanceRecordRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Avoid automatic replacement of DataSource
public class AttendanceRecordRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Test
    public void whenGetAttendanceRecordBySessionIdAndStudentId_thenReturnRecords() {
        // given
        Student student = new Student(LocalDate.of(1995, 5, 20), "student@example.com", "John", "Doe", "johndoe", "password", "2020F", 12345L, 67890L, 12345L, "barcode123");
        entityManager.persist(student);

        Location location = new Location("Main Hall", 200);
        entityManager.persist(location);

        Session session = new Session(LocalDate.of(2023, 6, 15), LocalTime.of(10, 0), LocalTime.of(12, 0));
        entityManager.persist(session);

        AttendanceRecord record = new AttendanceRecord(student, location);
        record.setSession(session);
        record.setScanDateTime(LocalDateTime.now());
        entityManager.persist(record);
        entityManager.flush();

        // when
        List<AttendanceRecord> foundRecords = attendanceRecordRepository.getAttendanceRecordBySessionIdAndStudentId(student.getId(), session.getId());

        // then
        assertThat(foundRecords).hasSize(1);
        assertThat(foundRecords.get(0).getStudent().getId()).isEqualTo(student.getId());
        assertThat(foundRecords.get(0).getSession().getId()).isEqualTo(session.getId());
    }

    @Test
    public void whenGetAttendanceRecordBySessionId_thenReturnRecords() {
        // given
        Student student = new Student(LocalDate.of(1995, 5, 20), "student@example.com", "John", "Doe", "johndoe", "password", "2020F", 12345L, 67890L, 12345L, "barcode123");
        entityManager.persist(student);

        Location location = new Location("Main Hall", 200);
        entityManager.persist(location);

        Session session = new Session(LocalDate.of(2023, 6, 15), LocalTime.of(10, 0), LocalTime.of(12, 0));
        entityManager.persist(session);

        AttendanceRecord record = new AttendanceRecord(student, location);
        record.setSession(session);
        record.setScanDateTime(LocalDateTime.now());
        entityManager.persist(record);
        entityManager.flush();

        // when
        List<AttendanceRecord> foundRecords = attendanceRecordRepository.getAttendanceRecordBySessionId(session.getId());

        // then
        assertThat(foundRecords).hasSize(1);
        assertThat(foundRecords.get(0).getSession().getId()).isEqualTo(session.getId());
    }

    @Test
    public void whenFindByStudentId_thenReturnRecords() {
        // given
        Student student = new Student(LocalDate.of(1995, 5, 20), "student@example.com", "John", "Doe", "johndoe", "password", "2020F", 12345L, 67890L, 12345L, "barcode123");
        entityManager.persist(student);

        Location location = new Location("Main Hall", 200);
        entityManager.persist(location);

        Session session = new Session(LocalDate.of(2023, 6, 15), LocalTime.of(10, 0), LocalTime.of(12, 0));
        entityManager.persist(session);

        AttendanceRecord record = new AttendanceRecord(student, location);
        record.setSession(session);
        record.setScanDateTime(LocalDateTime.now());
        entityManager.persist(record);
        entityManager.flush();

        // when
        Collection<AttendanceRecord> foundRecords = attendanceRecordRepository.findByStudentId(student.getId());
        AttendanceRecord firstRecord = null;
        if(!foundRecords.isEmpty()){
            Iterator<AttendanceRecord> iterator = foundRecords.iterator();
            if(iterator.hasNext()){
                firstRecord = iterator.next();
            }
        }
        // then
        assertThat(foundRecords).hasSize(1);
        assertThat(firstRecord.getStudent().getId()).isEqualTo(student.getId());
    }
}
