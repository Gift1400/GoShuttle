package za.ac.cput.GoShuttle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.GoShuttle.domain.RouteStops;

@Repository
public interface RouteStopsRepository extends JpaRepository<RouteStops, Integer> {

}
