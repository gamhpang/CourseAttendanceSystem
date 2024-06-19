package cs544.courseattendancesystem.repository;

import cs544.courseattendancesystem.domain.CourseOffering;
import cs544.courseattendancesystem.domain.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CourseOfferingRepository extends JpaRepository<CourseOffering, Long> {
     @Query("select c from CourseOffering c where c.endDate = :today")
    List<CourseOffering> findAllWithEndDateToday(@Param("today")LocalDate today);
}
