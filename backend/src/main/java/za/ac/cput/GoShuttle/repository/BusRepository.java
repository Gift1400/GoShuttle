package za.ac.cput.GoShuttle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.GoShuttle.domain.Bus;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus,Long> {
    @Override
    List<Bus> findAll();
    Bus findBusByBusID(Long busID);
}
