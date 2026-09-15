package za.ac.cput.GoShuttle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.GoShuttle.domain.LiveTrip;

import java.util.List;

@Repository
public interface LiveTripRepository extends JpaRepository<LiveTrip,Long> {
    @Override
    List<LiveTrip> findAll();
    LiveTrip findLiveTripByTripID(Long trip);
}
