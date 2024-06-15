package cs544.courseattendancesystem.repository;

import cs544.courseattendancesystem.domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
