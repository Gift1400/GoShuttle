package za.ac.cput.GoShuttle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.GoShuttle.domain.Boarding;

import java.util.List;

@Repository
public interface BoardingRepository extends JpaRepository<Boarding,Long> {
    @Override
    List<Boarding> findAll();
    Boarding findBoardingByBoardingID(Long boarding);
}
