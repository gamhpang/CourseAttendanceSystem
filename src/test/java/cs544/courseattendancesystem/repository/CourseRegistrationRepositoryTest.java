package cs544.courseattendancesystem.repository;

import cs544.courseattendancesystem.domain.CourseOffering;
import cs544.courseattendancesystem.domain.CourseRegistration;
import cs544.courseattendancesystem.domain.Student;
import cs544.courseattendancesystem.domain.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Avoid automatic replacement of DataSource
public class CourseRegistrationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;

    @Test
    public void testFindByStudentId() {
        // Given
        User user = new User();
        user.setUsername("john.doe");
        user.setPassword("password");  // Set all required fields for User entity

        entityManager.persist(user);
        entityManager.flush(); // Ensure user is persisted and has an ID assigned

        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setBirthDate(LocalDate.of(2000, 1, 1));
        student.setEmailAddress("john.doe@example.com");
        student.setUser(user);  // Set the user for the student

        // Save student
        entityManager.persist(student);
        entityManager.flush(); // Ensure student is persisted and has an ID assigned

        CourseOffering courseOffering = new CourseOffering();
        courseOffering.setRoom("Room 101");
        courseOffering.setStartDate(LocalDate.of(2023, 6, 1));
        courseOffering.setEndDate(LocalDate.of(2023, 12, 1));
        courseOffering.setCapacity(30);

        // Save courseOffering
        entityManager.persist(courseOffering);
        entityManager.flush(); // Ensure courseOffering is persisted and has an ID assigned

        CourseRegistration registration1 = new CourseRegistration();
        registration1.setStudent(student);
        registration1.setCourseOffering(courseOffering);
        registration1.setGrade("A");

        CourseRegistration registration2 = new CourseRegistration();
        registration2.setStudent(student);
        registration2.setCourseOffering(courseOffering);
        registration2.setGrade("B");

        courseRegistrationRepository.save(registration1);
        courseRegistrationRepository.save(registration2);

        // When
        List<CourseRegistration> registrations = courseRegistrationRepository.findByStudentId(student.getId());

        // Then
        Assertions.assertEquals(2, registrations.size());
        Assertions.assertTrue(registrations.stream().anyMatch(cr -> cr.getGrade().equals("A")));
        Assertions.assertTrue(registrations.stream().anyMatch(cr -> cr.getGrade().equals("B")));
    }

    @Test
    public void testFindByCourseOfferingId() {
        // Given
        User user1 = new User();
        user1.setUsername("alice.smith");
        user1.setPassword("password");

        User user2 = new User();
        user2.setUsername("bob.johnson");
        user2.setPassword("password");

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.flush(); // Ensure users are saved and have IDs assigned

        Student student1 = new Student();
        student1.setFirstName("Alice");
        student1.setLastName("Jane");
        student1.setBirthDate(LocalDate.of(2001, 2, 2));
        student1.setEmailAddress("alice.smith@example.com");
        student1.setUser(user1);  // Set the user for the student

        Student student2 = new Student();
        student2.setFirstName("Bob");
        student2.setLastName("Johnson");
        student2.setBirthDate(LocalDate.of(2002, 3, 3));
        student2.setEmailAddress("bob.johnson@example.com");
        student2.setUser(user2);  // Set the user for the student

        entityManager.persist(student1);
        entityManager.persist(student2);
        entityManager.flush(); // Ensure students are saved and have IDs assigned

        CourseOffering courseOffering1 = new CourseOffering();
        courseOffering1.setRoom("Room 101");
        courseOffering1.setStartDate(LocalDate.of(2023, 6, 1));
        courseOffering1.setEndDate(LocalDate.of(2023, 12, 1));
        courseOffering1.setCapacity(30);

        CourseOffering courseOffering2 = new CourseOffering();
        courseOffering2.setRoom("Room 202");
        courseOffering2.setStartDate(LocalDate.of(2023, 7, 1));
        courseOffering2.setEndDate(LocalDate.of(2023, 12, 1));
        courseOffering2.setCapacity(40);

        entityManager.persist(courseOffering1);
        entityManager.persist(courseOffering2);
        entityManager.flush(); // Ensure course offerings are saved and have IDs assigned

        CourseRegistration registration1 = new CourseRegistration();
        registration1.setStudent(student1);
        registration1.setCourseOffering(courseOffering1);
        registration1.setGrade("A");

        CourseRegistration registration2 = new CourseRegistration();
        registration2.setStudent(student2);
        registration2.setCourseOffering(courseOffering1);
        registration2.setGrade("B");

        CourseRegistration registration3 = new CourseRegistration();
        registration3.setStudent(student1);
        registration3.setCourseOffering(courseOffering2);
        registration3.setGrade("C");

        entityManager.persist(registration1);
        entityManager.persist(registration2);
        entityManager.persist(registration3);
        entityManager.flush(); // Flush to persist all entities

        // When
        List<CourseRegistration> registrations = courseRegistrationRepository.findByCourseOfferingId(courseOffering1.getId());

        // Then
        assertThat(registrations).isNotNull();
        assertThat(registrations.size()).isEqualTo(2);
        assertThat(registrations).extracting(CourseRegistration::getGrade)
                .containsExactlyInAnyOrder("A", "B");
    }
}
