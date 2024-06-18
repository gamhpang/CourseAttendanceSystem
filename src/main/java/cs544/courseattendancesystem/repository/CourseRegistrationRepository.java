package cs544.courseattendancesystem.repository;

import cs544.courseattendancesystem.domain.CourseRegistration;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {

    @Query("select cr from CourseRegistration cr where cr.student.id = :id")
    List<CourseRegistration> findByStudentId(@Param("id") long id);

    @Query("select cr from CourseRegistration cr where cr.courseOffering.id = :courseOfferingId")
    List<CourseRegistration> findByCourseOfferingId(@Param("courseOfferingId") long courseOfferingId);
}
