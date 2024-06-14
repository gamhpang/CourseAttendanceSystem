package cs544.courseattendancesystem.repository;

import cs544.courseattendancesystem.domain.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseOfferingRepository extends JpaRepository<CourseOffering, Long> {
}
