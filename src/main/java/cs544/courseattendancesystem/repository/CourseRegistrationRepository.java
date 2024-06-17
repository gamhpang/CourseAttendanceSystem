package cs544.courseattendancesystem.repository;

import cs544.courseattendancesystem.domain.CourseRegistration;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {

    @Query("select cr from CourseRegistration cr where cr.student.id = :id")
    List<CourseRegistration> findByStudentId(@Param("id") long id);
}
