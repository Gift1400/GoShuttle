package za.ac.cput.GoShuttle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.GoShuttle.domain.Driver;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {
    List<Driver> findAll();
    Driver findDriverByDriverID(Long driver);
}
