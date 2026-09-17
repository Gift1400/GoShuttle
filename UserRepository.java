package za.ac.cput.GoShuttle.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.GoShuttle.Entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByStudentNumber(String studentNumber);
    Optional<User> findByEmail(String email);
}
