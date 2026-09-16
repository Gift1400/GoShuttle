package za.ac.cput.GoShuttle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.GoShuttle.domain.Routes;
import java.util.*;

@Repository
public interface RoutesRepository extends JpaRepository<Routes, Integer> {
    List<Routes> findAll();
}
