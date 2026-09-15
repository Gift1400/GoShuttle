package za.ac.cput.goshuttle.repository;

import za.ac.cput.goshuttle.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findByCampus(String campus);

    List<Route> findByPickupArea(String pickupArea);
}