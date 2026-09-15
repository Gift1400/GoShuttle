package za.ac.cput.goshuttle.repository;

import za.ac.cput.goshuttle.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByStudentId(Long studentId);

    List<Booking> findByRouteId(Long routeId);

    List<Booking> findByBookingDate(LocalDate bookingDate);

    List<Booking> findByStudentIdAndStatus(Long studentId, String status);
}