package cs544.courseattendancesystem.repository;

import cs544.courseattendancesystem.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
