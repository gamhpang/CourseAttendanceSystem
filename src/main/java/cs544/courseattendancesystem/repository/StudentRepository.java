package cs544.courseattendancesystem.repository;

import cs544.courseattendancesystem.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Query to find students who haven't registered for a specific course offering
    @Query("SELECT s FROM Student s WHERE s.id NOT IN (SELECT cr.student.id FROM CourseRegistration cr WHERE cr.courseOffering.id = :courseOfferingId)")
    List<Student> findStudentsNotRegistered(@Param("courseOfferingId") long courseOfferingId);

}
