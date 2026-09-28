package za.ac.cput.GoShuttle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.GoShuttle.domain.Pass;

import java.util.List;

@Repository
public interface PassRepository extends JpaRepository<Pass, String> {
    List<Pass> findAll();
    Pass getPassByPassId(String passId);
}
