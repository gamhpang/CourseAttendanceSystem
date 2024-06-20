package cs544.courseattendancesystem.repository;

import cs544.courseattendancesystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM res_user WHERE username = ?1", nativeQuery = true)
    Optional<User> findByUserName(String username);
}
