package cs544.courseattendancesystem.repository;

import cs544.courseattendancesystem.domain.CourseOffering;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Avoid automatic replacement of DataSource
public class CourseOfferingRepositoryTest {

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Test
    public void whenFindAllWithEndDateToday_thenReturnCourseOfferings() {
        // given
        LocalDate today = LocalDate.now();
        CourseOffering courseOffering1 = new CourseOffering();
        courseOffering1.setEndDate(today);
        courseOfferingRepository.save(courseOffering1);

        CourseOffering courseOffering2 = new CourseOffering();
        courseOffering2.setEndDate(today);
        courseOfferingRepository.save(courseOffering2);

        CourseOffering courseOffering3 = new CourseOffering();
        courseOffering3.setEndDate(today.plusDays(1)); // Not ending today
        courseOfferingRepository.save(courseOffering3);

        // when
        List<CourseOffering> foundCourseOfferings = courseOfferingRepository.findAllWithEndDateToday(today);

        // then
        assertThat(foundCourseOfferings).hasSize(2);
        assertThat(foundCourseOfferings).extracting(CourseOffering::getEndDate).containsOnly(today);
    }
}
