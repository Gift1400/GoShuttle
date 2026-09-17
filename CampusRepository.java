package za.ac.cput.GoShuttle.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.GoShuttle.Entity.Campus;

@Repository
public interface CampusRepository extends JpaRepository<Campus, Long> {
}
